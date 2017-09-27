package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.ReqRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler, RescueTeamConsumer}
import it.unibo.drescue.geocoding.{Geocoding, GeocodingException, GeocodingImpl}
import it.unibo.drescue.localModel.{EnrolledTeamInfo, Observers}
import it.unibo.drescue.model.{RescueTeamImpl, RescueTeamImplBuilder}
import it.unibo.drescue.view.CustomDialog
import org.slf4j.{Logger, LoggerFactory}

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert

/**
  * Object companion of EnrollTeamControllerImpl class
  */
object EnrollTeamControllerImpl extends Enumeration {
  val CommandFillAll: String = "Fill all data."
  val CommandInsertValidAddress: String = "Insert a valid address"
  val CommandDuplicated: String = "Error inserting the team, teamID duplicated"
  val Added: String = "The team has been added."
  val Adding: String = "All data ok, adding the team."
  val Processing: String = "Processing"
  val EmptyTeamData = "EmptyTeamData"
  val InvalidAddress = "InvalidAddress"
  val Checking = "Checking"
  val Error = "Error"
  val EnrollOK = "The team has been successfully enrolled."
  val SelectTeam = "Nothing selected"
}

/**
  * A class representing the enroll team controller
  * @param mainController the main controller
  * @param rabbitMQ the channel used to handle requests and responses
  */
class EnrollTeamControllerImpl(private var mainController: MainControllerImpl, val rabbitMQ: RabbitMQImpl) extends Observer {

  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  mainController.model.addObserver(Observers.EnrollTeam, this)
  private val Logger: Logger = LoggerFactory getLogger classOf[EnrollTeamControllerImpl]
  var obsBuffer = new ObservableBuffer[String]()
  var dialog: Alert = _

  /**
    * Starts checks to verify if all fields are filled and if the inserted address is correct
    *
    * @param rescueTeamId the inserted rescue team ID
    * @param name         the inserted rescue team name
    * @param address      the inserted rescue team address
    * @param phoneNumber  the inserted rescue team phone number
    */
  def startChecks(rescueTeamId: String, name: String, address: String, phoneNumber: String) = {

    checkInputs(rescueTeamId, name, address, phoneNumber) match {
      case false => startDialogAndWait(EnrollTeamControllerImpl.EmptyTeamData)
      case true =>
        startDialog(EnrollTeamControllerImpl.Checking)
        val geocoding: Geocoding = new GeocodingImpl()
        var latitude: Double = 0.0
        var longitude: Double = 0.0
        try {
          val latlng = geocoding.getLatLng(address)
          latitude = latlng.get("lat").getAsDouble
          longitude = latlng.get("lng").getAsDouble
        } catch {
          case e: GeocodingException =>
            mainController.changeView("NewTeam")
            startDialogAndWait(EnrollTeamControllerImpl.InvalidAddress)
            Logger error ("EnrollTeam", "Address not valid")
            EnrollTeamControllerImpl.CommandInsertValidAddress
          case _: Throwable =>
            mainController.changeView("NewTeam")
            startDialogAndWait(EnrollTeamControllerImpl.Error)
            Logger error ("EnrollTeam", "Address not valid")
            EnrollTeamControllerImpl.Error
        }

        Logger info ("[EnrollTeam] : lat " + latitude + " long: " + longitude)
        if (latitude == 0.0 || longitude == 0.0) {
          Logger error ("EnrollTeam", "Error in geocoding")
          EnrollTeamControllerImpl.Error
        } else {
          addTeam(rescueTeamId, name, address, phoneNumber, latitude, longitude)
        }
    }
  }

  /**
    * Adds the new rescue team both into the database and in the displayed list
    *
    * @param rescueTeamId the new rescue team ID
    * @param name         the new rescue team name
    * @param address      the new rescue team address
    * @param phoneNumber  the new rescue team phone number
    * @param latitude     the new rescue team latitude
    * @param longitude    the new rescue team longitude
    * @return a string representing the result action
    */
  def addTeam(rescueTeamId: String, name: String, address: String, phoneNumber: String, latitude: Double, longitude: Double): String = {

    mainController.changeView("NewTeam")
    startDialog(EnrollTeamControllerImpl.Processing)

    val message: Message = NewRescueTeamMessage(
      rescueTeamID = rescueTeamId,
      rescueTeamName = name,
      rescueTeamLatitude = latitude,
      rescueTeamLongitude = longitude,
      rescueTeamPassword = "",
      rescueTeamPhoneNumber = phoneNumber
    )
    val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
    val response: String = task.get()
    Logger info ("EnrollTeam controller: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

    messageName match {

      case MessageType.SUCCESSFUL_MESSAGE => {
        Logger info ("[EnrollTeam] : Team added: " + rescueTeamId)
        val notEnrolledTeams = mainController.model.notEnrolledRescueTeams
        notEnrolledTeams.add(new RescueTeamImplBuilder().setRescueTeamID(rescueTeamId)
          .setPassword("")
          .setName(name)
          .setLatitude(latitude)
          .setLongitude(longitude)
          .setPhoneNumber(phoneNumber)
          .createRescueTeamImpl())
        mainController.model.notEnrolledRescueTeams = notEnrolledTeams

        mainController.changeView("NewTeam")
        startDialogAndWait(EnrollTeamControllerImpl.Added)
      }
      case MessageType.ERROR_MESSAGE => {
        Logger error ("EnrollTeam", "Team not added, ERROR: " + rescueTeamId)
        mainController.changeView("NewTeam")
        val customDialog = new CustomDialog(mainController)
        customDialog.setErrorText(GsonUtils.fromGson(response, classOf[ErrorMessageImpl]).getError)
        dialog = customDialog.createDialog(EnrollTeamControllerImpl.Error)
        dialog.showAndWait()
      }
      case _ => // do nothing
    }
    EnrollTeamControllerImpl.Adding
  }

  /**
    * Starts a new custom dialog without buttons
    *
    * @param newDialog a string representing the new dialog information
    */
  def startDialog(newDialog: String) = {
    dialog = new CustomDialog(mainController).createDialog(newDialog)
    dialog.show()
  }

  /**
    * Starts a new custom dialog with OK button and waits for the user to click on it
    *
    * @param newDialog a string representing the new dialog information
    */
  def startDialogAndWait(newDialog: String) = {
    dialog = new CustomDialog(mainController).createDialog(newDialog)
    dialog.showAndWait()
  }

  /**
    * Checks if all input fields are filled
    *
    * @param rescueTeamId the text in rescue team ID field
    * @param name         the text in rescue team name field
    * @param address      the text in rescue team address field
    * @param phoneNumber  the text in rescue team phone number field
    * @return true, if all inputs are filled
    *         false, otherwise
    */
  def checkInputs(rescueTeamId: String, name: String, address: String, phoneNumber: String): Boolean = {
    StringUtils.isAValidString(rescueTeamId) && StringUtils.isAValidString(name) &&
      StringUtils.isAValidString(phoneNumber) && StringUtils.isAValidString(address)
  }

  /**
    * Verifies if a team is selected and performs actions to save the new enrollment
    * to the database after the user clicks on the select button
    *
    * @param selectedTeamID a string representing the selected team
    */
  def selectPress(selectedTeamID: String) = {

    if (StringUtils.isAValidString(selectedTeamID)) {

      val message: Message = EnrollRescueTeamMessageImpl(selectedTeamID, mainController.model.cpID)
      val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
      val response: String = task.get()
      Logger info ("EnrollTeam controller: " + response)
      val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

      messageName match {
        case MessageType.SUCCESSFUL_MESSAGE =>

          startDialogAndWait(EnrollTeamControllerImpl.EnrollOK)

          var indexToChange: Int = -1
          mainController.model.notEnrolledRescueTeams forEach ((rescueTeam: RescueTeamImpl) => {
            val rescueTeamID = rescueTeam.getRescueTeamID
            if (rescueTeamID == selectedTeamID) {
              indexToChange = mainController.model.notEnrolledRescueTeams.indexOf(rescueTeam)
            }
          })

          val newEnrollment = mainController.model.notEnrolledRescueTeams.get(indexToChange)

          val enrolledInfoList = mainController.model.enrolledTeamInfoList
          if (indexToChange != -1) {

            val newTeam = new RescueTeamImplBuilder()
              .setRescueTeamID(newEnrollment.getRescueTeamID)
              .setName(newEnrollment.getName)
              .setPhoneNumber(newEnrollment.getPhoneNumber)
              .createRescueTeamImpl()

            val enrolledList = mainController.model.enrolledRescueTeams
            enrolledList.add(newTeam)
            mainController.model.enrolledRescueTeams = enrolledList

            val newTeamInfo = new EnrolledTeamInfo(
              newEnrollment.getRescueTeamID,
              newEnrollment.getName,
              newEnrollment.getPhoneNumber,
              true,
              "",
              "")
            mainController.model.addEnrollment(newTeamInfo)

            val rescueTeamConditionMessage = new ReqRescueTeamConditionMessageBuilderImpl()
              .setRescueTeamID(newEnrollment.getRescueTeamID)
              .setFrom(mainController.model.cpID)
              .build()

            mainController.rabbitMQ.bindQueueToExchange(mainController.queueName, mainController.ExchangeName, newEnrollment)
            mainController.rabbitMQ.sendMessage(mainController.ExchangeName,
              newEnrollment.getRescueTeamID, null, rescueTeamConditionMessage)

            mainController.changeView("Home")

          }

        case MessageType.ERROR_MESSAGE =>
          startDialogAndWait(EnrollTeamControllerImpl.Error)

        case _ => //do nothing

      }
    } else {
      startDialogAndWait(EnrollTeamControllerImpl.SelectTeam)
    }
  }

  /**
    * Changes the view returning to the home view after the user clicks on back button
    */
  def backPress() = {
    mainController.changeView("Home")
  }

  override def onReceivingNotification(): Unit = {
    obsBuffer.clear()
    mainController.model.notEnrolledRescueTeams.forEach(
      (rescueTeam: RescueTeamImpl) => {
        obsBuffer add rescueTeam.getRescueTeamID
      }
    )
  }

}
