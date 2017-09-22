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
import scalafx.scene.control.Alert

object ManageRescuesControllerImpl extends Enumeration {
  val Sent: String = "Team notified"
  var Error: String = "Team working"
}

class ManageRescuesControllerImpl(private var mainController: MainControllerImpl,
                                  var rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.ManageRescue, this)
  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  //Coordinator configuration
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

  def sendPressed(wantedRescueTeamID: String) = {
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
    println("associated cp" + cpIDList)

    //TODO start Ricart Agrawala's Algorithm to occupy the given rescueTeamID
    coordinator.setCondition(CoordinatorCondition.WANTED)
    coordinator.setCsName(wantedRescueTeamID)
    coordinator.setReqTimestamp(new Timestamp(System.currentTimeMillis()))
    coordinator.createPendingCivilProtectionReplayStructure(cpIDList)

    //Broadcast request
    val reqCoordinationMessage: ReqCoordinationMessage = new messages.ReqCoordinationMessage()
    reqCoordinationMessage.setFrom(mainController.model.cpID)
    reqCoordinationMessage.setRescueTeamID(wantedRescueTeamID)
    reqCoordinationMessage.setTimestamp(coordinator.getReqTimestamp)

    rabbitMQ.sendMessage(mainController.ExchangeName, wantedRescueTeamID, null, reqCoordinationMessage)

    //The process is blocked as long as enter in cs (coordinator condition == HELD) or has received a negative responde (RS = OCCUPIED)
    //TODO Thread or timeout?
    while (coordinator.getCondition != CoordinatorCondition.HELD && coordinator.getCondition != CoordinatorCondition.DETACHED) {
      println(coordinator.getCondition)
    }
    if (coordinator.getCondition == CoordinatorCondition.HELD) {

      println("[CS Execution]")
      var indexToChange: Int = -1
      var rescueTeamToChange: EnrolledTeamInfo = null
      val list = mainController.model.enrolledTeamInfoList
      list forEach ((rescueTeam: EnrolledTeamInfo) => {
        if (rescueTeam.teamID.value == wantedRescueTeamID) {
          indexToChange = list.indexOf(rescueTeam)
          rescueTeamToChange = list.get(indexToChange)
        }
      })

      //TODO get alert ID
      list.set(indexToChange, new EnrolledTeamInfo(
        rescueTeamToChange.teamID.value,
        rescueTeamToChange.teamName.value,
        rescueTeamToChange.phoneNumber.value,
        false,
        mainController.model.cpID,
        rescueTeamToChange.alertID.value)
      )
      mainController.model.enrolledTeamInfoList = list

      //the process came back from CS
      coordinator.backToCs(RescueTeamCondition.OCCUPIED)

      //TODO send a message to inform other cp
      sendReplyRescueTeamCondition(wantedRescueTeamID, RescueTeamCondition.OCCUPIED)

      startSuccessDialog()
      mainController.changeView("Home")
    }
    else {
      startErrorDialog()
    }
  }

  def startSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Sent)
    dialog.showAndWait()
  }

  def startErrorDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Error)
    dialog.showAndWait()
  }

  def stopPressed(wantedRescueTeamID: String) = {

    //TODO stop the given rescueTeamID, change local state of rescueTeamID in available
    var indexToChange: Int = -1
    var rescueTeamToChange: EnrolledTeamInfo = null
    val list = mainController.model.enrolledTeamInfoList

    list forEach ((rescueTeam: EnrolledTeamInfo) => {
      if (rescueTeam.teamID.value == wantedRescueTeamID) {
        indexToChange = list.indexOf(rescueTeam)
        rescueTeamToChange = list.get(indexToChange)
      }
    })

    if (rescueTeamToChange.cpID == mainController.model.cpID) {
      list.set(indexToChange, new EnrolledTeamInfo(
        rescueTeamToChange.teamID.value,
        rescueTeamToChange.teamName.value,
        rescueTeamToChange.phoneNumber.value,
        true,
        "",
        "")
      )
      mainController.model.enrolledTeamInfoList = list

      //TODO send message to the other CP with in order to update their view
      sendReplyRescueTeamCondition(wantedRescueTeamID, RescueTeamCondition.AVAILABLE)

      //TODO confirm dialog
      mainController.changeView("ManageRescues")
    } else {
      //TODO error message
    }

  }

  def sendReplyRescueTeamCondition(rescueTeamID: String, rescueTeamCondition: RescueTeamCondition) = {
    var reply: Message = null
    reply = new ReplyRescueTeamConditionMessageBuilderImpl()
      .setRescueTeamID(rescueTeamID)
      .setRescueTeamCondition(rescueTeamCondition)
      .setFrom(mainController.model.cpID)
      .build()
    rabbitMQ.sendMessage(mainController.ExchangeName, rescueTeamID, null, reply)
  }

  def backPress() = {
    mainController.changeView("Home")
  }

  def updateEnrollTeamInfo(wantedRescueTeamID: String, enrolledTeamInfo: EnrolledTeamInfo) = {
    //TODO same coda stop and send button
  }

}
