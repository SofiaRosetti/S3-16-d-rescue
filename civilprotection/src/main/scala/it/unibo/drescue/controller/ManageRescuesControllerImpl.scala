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

  //TODO check scaladoc
  /**
    * Performs the actions relative to the click on the send button.
    *
    * @param wantedRescueTeamID
    * @param alertID
    * @return
    */
  def sendPressed(wantedRescueTeamID: String, alertID: String): Any = {
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
        println("Associated cp" + cpIDList)

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

  def sendReplyRescueTeamCondition(rescueTeamID: String, rescueTeamCondition: RescueTeamCondition): Unit = {
    var reply: Message = null
    reply = new ReplyRescueTeamConditionMessageBuilderImpl()
      .setRescueTeamID(rescueTeamID)
      .setRescueTeamCondition(rescueTeamCondition)
      .setFrom(mainController.model.cpID)
      .build()
    rabbitMQ.sendMessage(mainController.ExchangeName, rescueTeamID, null, reply)
  }

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
    *
    * @return
    */
  def startSuccessDialog(): Option[ButtonType] = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Sent)
    dialog.showAndWait()
  }

  /**
    * Starts a custom error dialog
    *
    * @return
    */
  def startErrorDialog(): Option[ButtonType] = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Error)
    dialog.showAndWait()
  }

  /**
    * Performs the ations relative to the click on the stop button
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

  def stopSuccessDialog(): Option[ButtonType] = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Stop)
    dialog.showAndWait()
  }

  def stopNotAuthorizedErrorDialog(): Option[ButtonType] = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.StopNotAuthorized)
    dialog.showAndWait()
  }

  /**
    * Changes the view to the home view after the click on the back button
    */
  def backPress(): Unit = {
    mainController.changeView("Home")
  }


}
