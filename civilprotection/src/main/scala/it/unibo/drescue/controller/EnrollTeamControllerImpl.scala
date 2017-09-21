package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.messages.{Message, MessageType, MessageUtils, NewRescueTeamMessage}
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.geocoding.{Geocoding, GeocodingException, GeocodingImpl}
import it.unibo.drescue.localModel.Observers
import it.unibo.drescue.model.{RescueTeamImpl, RescueTeamImplBuilder}
import it.unibo.drescue.view.CustomDialog

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert

object EnrollTeamControllerImpl extends Enumeration {
  val CommandFillAll: String = "Fill all data."
  val CommandInsertValidAddress: String = "Insert a valid address"
  val CommandDuplicated: String = "Error inserting the team, teamID duplicated"
  val Error: String = "An error occurred"
  val Added: String = "The team has been added."
  val Adding: String = "All data ok, adding the team."
  val Processing: String = "Processing"
}

class EnrollTeamControllerImpl(private var mainController: MainControllerImpl, val rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.EnrollTeam, this)
  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  var obsBuffer = new ObservableBuffer[String]()
  var dialog: Alert = _

  def checkInputsAndAdd(rescueTeamId: String, name: String, address: String, phoneNumber: String): String = {

    if (!checkInputs(rescueTeamId, name, address, phoneNumber)) {
      //TODO show dialog 'fill al data'
      println("[EnrollTeam] : Fill all data")
      EnrollTeamControllerImpl.CommandFillAll
    }

    val geocoding: Geocoding = new GeocodingImpl()
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    try {
      val latlng = geocoding.getLatLng(address)
      latitude = latlng.get("lat").getAsDouble
      longitude = latlng.get("lng").getAsDouble
    } catch {
      case e: GeocodingException => //TODO show dialog "insert a correct address"
        println("[EnrollTeam] : Address not valid")
        EnrollTeamControllerImpl.CommandInsertValidAddress
      case _ => //TODO show dialog "an error occurred"
        println("[EnrollTeam] : Address not valid")
        EnrollTeamControllerImpl.Error
    }

    println("[EnrollTeam] : lat " + latitude + " long: " + longitude)
    if (latitude == 0.0 || longitude == 0.0) {
      println("[EnrollTeam] Error in geocoding")
      EnrollTeamControllerImpl.Error
    }

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
        // stop dialog and delete all fields / change view to this
      }
      case MessageType.ERROR_MESSAGE => {
        println("[EnrollTeam] : Team not added, ERROR " + rescueTeamId)
        // show ERROR -> change dialog
        //TODO
        //if duplicated -> return CommandDuplicated
      }
    }

    EnrollTeamControllerImpl.Adding
  }

  def checkInputs(rescueTeamId: String, name: String, address: String, phoneNumber: String): Boolean = {
    StringUtils.isAValidString(rescueTeamId) && StringUtils.isAValidString(name) &&
      StringUtils.isAValidString(phoneNumber) && StringUtils.isAValidString(address)
  }

  //TODO start here a request for GetAllRescueTeam

  def startSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.Added)
    dialog.showAndWait()
  }

  def startProcessingDialog() = {
    dialog = new CustomDialog(mainController).createDialog(EnrollTeamControllerImpl.Processing)
    dialog.show()
  }

  def selectPress() = {
    mainController.changeView("Home")
    //TODO start thread with future
    //when future return add the returns
    // OK -> back to home
    // ERROR -> dialog
  }

  def backPress() = {
    mainController.changeView("Home")
  }

  /**
    * TODO
    */
  override def onReceivingNotification(): Unit = {
    obsBuffer.clear()
    mainController.model.notEnrolledRescueTeams.forEach(
      (rescueTeam: RescueTeamImpl) => {
        obsBuffer add rescueTeam.getRescueTeamID
        println("[EnrolledTeamController]: notification for: " + rescueTeam.toPrintableString)
      }
    )
  }

}
