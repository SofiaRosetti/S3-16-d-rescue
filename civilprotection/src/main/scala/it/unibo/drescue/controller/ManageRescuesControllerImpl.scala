package it.unibo.drescue.controller

import java.sql.Timestamp
import java.util
import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.communication.builder.ReplyRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.messages.{CPsMessageImpl, GetAssociatedCpMessageImpl, Message, ReqCoordinationMessage}
import it.unibo.drescue.communication.{GsonUtils, messages}
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.localModel.{EnrolledTeamInfo, Observers}
import it.unibo.drescue.model.CivilProtectionImpl
import it.unibo.drescue.utils.{Coordinator, CoordinatorCondition, CoordinatorImpl, RescueTeamCondition}
import it.unibo.drescue.view.CustomDialog

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Alert, ButtonType}

object ManageRescuesControllerImpl extends Enumeration {
  val Sent: String = "Team notified"
  val Stop: String = "Team stop"
  val StopNotAuthorized: String = "Stop not authorized"
  var Error: String = "Team working"
}

class ManageRescuesControllerImpl(private var mainController: MainControllerImpl,
                                  var rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.ManageRescue, this)
  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  val coordinator: Coordinator = CoordinatorImpl.getInstance()
  var obsBuffer = new ObservableBuffer[EnrolledTeamInfo]()
  var dialog: Alert = _
  coordinator.setConnection(rabbitMQ)
  coordinator.setExchange(mainController.ExchangeName)
  coordinator.setMyID(mainController.model.cpID)

  override def onReceivingNotification(): Unit = {
    obsBuffer.clear()
    mainController.model.enrolledTeamInfoList.forEach(
      (enrolledTeamInfo: EnrolledTeamInfo) => {
        obsBuffer add enrolledTeamInfo
      }
    )
  }

  /**
    * Performs the actions relative to the click on the send button.
    * The civil protection send a broadcast message to other civil protections that has enrolled in the same rescue team
    * in order to received their local condition and waits until it received all the reply message or a reply that inform it that
    * the rescue team is already occupied
    *
    * @param wantedRescueTeamID the rescue team that the civil protection wants to occupied
    * @param alertID the alert for which the civil protection wants to start rescue
    * @return
    */
  def sendPressed(wantedRescueTeamID: String, alertID: String): Unit = {
    if (wantedRescueTeamID != "") {
      var indexToChange: Int = -1
      var rescueTeamToChange: EnrolledTeamInfo = null
      val list = mainController.model.enrolledTeamInfoList
      list forEach ((rescueTeam: EnrolledTeamInfo) => {
        if (rescueTeam.teamID.value == wantedRescueTeamID) {
          indexToChange = list.indexOf(rescueTeam)
          rescueTeamToChange = list.get(indexToChange)
        }
      })

      if (rescueTeamToChange.availability.value == "false" && rescueTeamToChange.cpID.value == mainController.model.cpID) {
        startErrorDialog()
      }
      else {
        val message: Message = GetAssociatedCpMessageImpl(wantedRescueTeamID)
        val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
        val response: String = task.get()
        println("Manage Rescues Controller - cp: " + response)
        val associatedCpMessage = GsonUtils.fromGson(response, classOf[CPsMessageImpl])
        val cpList: java.util.List[CivilProtectionImpl] = associatedCpMessage.cpList

        val cpIDList: java.util.List[String] = new util.ArrayList[String]()
        cpList.forEach((cp: CivilProtectionImpl) => {
          if (cp.getCpID != mainController.model.cpID) {
            cpIDList.add(cp.getCpID)
          }
        })

        if (cpIDList.size() > 0) {
          coordinator.setCondition(CoordinatorCondition.WANTED)
          coordinator.setCsName(wantedRescueTeamID)
          coordinator.setReqTimestamp(new Timestamp(System.currentTimeMillis()))
          coordinator.createPendingCivilProtectionReplayStructure(cpIDList)

          val reqCoordinationMessage: ReqCoordinationMessage = new messages.ReqCoordinationMessage()
          reqCoordinationMessage.setFrom(mainController.model.cpID)
          reqCoordinationMessage.setRescueTeamID(wantedRescueTeamID)
          reqCoordinationMessage.setTimestamp(coordinator.getReqTimestamp)

          rabbitMQ.sendMessage(mainController.ExchangeName, wantedRescueTeamID, null, reqCoordinationMessage)

          while (coordinator.getCondition != CoordinatorCondition.HELD && coordinator.getCondition != CoordinatorCondition.DETACHED) {
            println(coordinator.getCondition)
          }
          if (coordinator.getCondition == CoordinatorCondition.HELD) {
            criticalSectionExecution(alertID, rescueTeamToChange, indexToChange)
            sendReplyRescueTeamCondition(wantedRescueTeamID, RescueTeamCondition.OCCUPIED)
            coordinator.backToCs(RescueTeamCondition.OCCUPIED)

            startSuccessDialog()
            mainController.changeView("Home")
          }
          else {
            startErrorDialog()
          }
        }
        else {
          criticalSectionExecution(alertID, rescueTeamToChange, indexToChange)
          startSuccessDialog()
          mainController.changeView("Home")
        }
      }
    }
  }

  /**
    * Performs the actions relative to the click on the stop button
    * The civil protection stopped the rescue team and send a message to other civil protection about the new condition of rescue team
    *
    * @param wantedRescueTeamID
    * @return
    */
  def stopPressed(wantedRescueTeamID: String): Any = {
    var indexToChange: Int = -1
    var rescueTeamToChange: EnrolledTeamInfo = null
    val list = mainController.model.enrolledTeamInfoList
    list forEach ((rescueTeam: EnrolledTeamInfo) => {
      if (rescueTeam.teamID.value == wantedRescueTeamID) {
        indexToChange = list.indexOf(rescueTeam)
        rescueTeamToChange = list.get(indexToChange)
      }
    })
    if (rescueTeamToChange.cpID.value != "") {
      if (rescueTeamToChange.cpID.value == mainController.model.cpID) {
        mainController.model.modifyEnrollment(indexToChange, new EnrolledTeamInfo(
          rescueTeamToChange.teamID.value,
          rescueTeamToChange.teamName.value,
          rescueTeamToChange.phoneNumber.value,
          true,
          "",
          "")
        )

        sendReplyRescueTeamCondition(wantedRescueTeamID, RescueTeamCondition.AVAILABLE)
        stopSuccessDialog()
      } else {
        stopNotAuthorizedErrorDialog()
      }
    }
  }

  /**
    * Changes the view to the home view after the click on the back button
    */
  def backPress(): Unit = {
    mainController.changeView("Home")
  }

  /**
    * Send message to the other civil protection in order to inform them that the rescue team condition has changed
    *
    * @param rescueTeamID the ID of the rescue team that has changed
    * @param rescueTeamCondition the new rescue team condition
    */
  def sendReplyRescueTeamCondition(rescueTeamID: String, rescueTeamCondition: RescueTeamCondition): Unit = {
    var reply: Message = null
    reply = new ReplyRescueTeamConditionMessageBuilderImpl()
      .setRescueTeamID(rescueTeamID)
      .setRescueTeamCondition(rescueTeamCondition)
      .setFrom(mainController.model.cpID)
      .build()
    rabbitMQ.sendMessage(mainController.ExchangeName, rescueTeamID, null, reply)
  }

  /**
    * Change the local condition of the rescue team
    *
    * @param alertID the alert for which the rescue is occupied
    * @param rescueTeamToChange the changed rescue team
    * @param indexToChange the index into local structure of the changed rescue team
    */
  def criticalSectionExecution(alertID: String, rescueTeamToChange: EnrolledTeamInfo, indexToChange: Integer): Unit = {
    println("[CS Execution]")
    mainController.model.modifyEnrollment(indexToChange, new EnrolledTeamInfo(
      rescueTeamToChange.teamID.value,
      rescueTeamToChange.teamName.value,
      rescueTeamToChange.phoneNumber.value,
      false,
      mainController.model.cpID,
      alertID)
    )
  }

  /**
    * Starts a custom dialog which informs the user that the team has been sent
    */
  def startSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Sent)
    dialog.showAndWait()
  }

  /**
    *  Starts a custom dialog which informs the user that the team has been occupied
    */
  def startErrorDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Error)
    dialog.showAndWait()
  }

  /**
    * Starts a custom dialog which informs the user that the team has been stopped
    */
  def stopSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Stop)
    dialog.showAndWait()
  }

  /**
    * Starts a custom dialog which informs the user that he/she is not authorized to stop the rescue
    */
  def stopNotAuthorizedErrorDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.StopNotAuthorized)
    dialog.showAndWait()
  }

}
