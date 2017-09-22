package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.ReqRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.geocoding.{Geocoding, GeocodingException, GeocodingImpl}
import it.unibo.drescue.localModel.{EnrolledTeamInfo, Observers}
import it.unibo.drescue.model.{RescueTeamImpl, RescueTeamImplBuilder}
import it.unibo.drescue.view.CustomDialog

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert

object EnrollTeamControllerImpl extends Enumeration {
  val CommandFillAll: String = "Fill all data."
  val CommandInsertValidAddress: String = "Insert a valid address"
  val CommandDuplicated: String = "Error inserting the team, teamID duplicated"
  //val Error: String = "An error occurred"
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

class EnrollTeamControllerImpl(private var mainController: MainControllerImpl, val rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.EnrollTeam, this)
  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  var obsBuffer = new ObservableBuffer[String]()
  var dialog: Alert = _

  def startChecks(rescueTeamId: String, name: String, address: String, phoneNumber: String) = {

    checkInputs(rescueTeamId, name, address, phoneNumber) match {
      case false => startFillAllDataDialog()
      case true =>
        startCheckingDataDialog()
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
            startAddressDialog()
            println("[EnrollTeam] : Address not valid")
            EnrollTeamControllerImpl.CommandInsertValidAddress
          case _: Throwable =>
            mainController.changeView("NewTeam")
            startErrorDialog()
            println("[EnrollTeam] : Address not valid")
            EnrollTeamControllerImpl.Error
        }

        println("[EnrollTeam] : lat " + latitude + " long: " + longitude)
        if (latitude == 0.0 || longitude == 0.0) {
          println("[EnrollTeam] Error in geocoding")
          EnrollTeamControllerImpl.Error
        } else {
          addTeam(rescueTeamId, name, address, phoneNumber, latitude, longitude)
        }
    }
  }

  def addTeam(rescueTeamId: String, name: String, address: String, phoneNumber: String, latitude: Double, longitude: Double): String = {

    mainController.changeView("NewTeam")
    startProcessingDialog()

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
    println("EnrollTeam controller: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

    messageName match {

      case MessageType.SUCCESSFUL_MESSAGE => {
        println("[EnrollTeam] : Team added: " + rescueTeamId)
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
        startSuccessDialog()
      }
      case MessageType.ERROR_MESSAGE => {
        println("[EnrollTeam] : Team not added, ERROR " + rescueTeamId)
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

  def startSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.Added)
    dialog.showAndWait()
  }

  def startProcessingDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.Processing)
    dialog.show()
  }

  def startAddressDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.InvalidAddress)
    dialog.showAndWait()
  }

  def checkInputs(rescueTeamId: String, name: String, address: String, phoneNumber: String): Boolean = {
    StringUtils.isAValidString(rescueTeamId) && StringUtils.isAValidString(name) &&
      StringUtils.isAValidString(phoneNumber) && StringUtils.isAValidString(address)
  }

  def startFillAllDataDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.EmptyTeamData)
    dialog.showAndWait()
  }

  def startCheckingDataDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.Checking)
    dialog.show()
  }

  def selectPress(selectedTeamID: String) = {

    if (StringUtils.isAValidString(selectedTeamID)) {

      val message: Message = EnrollRescueTeamMessageImpl(selectedTeamID, mainController.model.cpID)
      val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
      val response: String = task.get()
      println("EnrollTeam controller: " + response)
      val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

      messageName match {
        case MessageType.SUCCESSFUL_MESSAGE =>

          startEnrollOkDialog()

          //add rescue team to rescueTeamList and enrolledTeamInfoList
          var indexToChange: Int = -1
          mainController.model.notEnrolledRescueTeams forEach ((rescueTeam: RescueTeamImpl) => {
            val rescueTeamID = rescueTeam.getRescueTeamID
            if (rescueTeamID == selectedTeamID) {
              indexToChange = mainController.model.notEnrolledRescueTeams.indexOf(rescueTeam)
            }
          })

          val notEnrolledList = mainController.model.notEnrolledRescueTeams
          val newEnrollment = notEnrolledList.get(indexToChange)

          val enrolledList = mainController.model.enrolledRescueTeams
          val enrolledInfoList = mainController.model.enrolledTeamInfoList
          if (indexToChange != -1) {

            val newTeam = new RescueTeamImplBuilder()
              .setRescueTeamID(newEnrollment.getRescueTeamID)
              .setName(newEnrollment.getName)
              .setPhoneNumber(newEnrollment.getPhoneNumber)
              .createRescueTeamImpl()
            enrolledList.add(newTeam)
            mainController.model.enrolledRescueTeams = enrolledList

            val newTeamInfo = new EnrolledTeamInfo(
              newEnrollment.getRescueTeamID,
              newEnrollment.getName,
              newEnrollment.getPhoneNumber,
              true,
              "",
              "")
            enrolledInfoList.add(newTeamInfo)
            mainController.model.enrolledTeamInfoList = enrolledInfoList

            //send message to get availability
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
          startErrorDialog()

        case _ => //do nothing

      }
    } else {
      startSelectTeamDialog()
    }
  }

  def startSelectTeamDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.SelectTeam)
    dialog.showAndWait()
  }

  def startErrorDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.Error)
    dialog.showAndWait()
  }

  def startEnrollOkDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.EnrollOK)
    dialog.showAndWait()
  }

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
