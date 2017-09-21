package it.unibo.drescue.controller

import java.sql.Timestamp
import java.util
import java.util.concurrent.Future

import it.unibo.drescue.communication.messages.{CPsMessageImpl, GetAssociatedCpMessageImpl, Message, ReqCoordinationMessage}
import it.unibo.drescue.communication.{GsonUtils, messages}
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.localModel.{EnrolledTeamInfo, Observers}
import it.unibo.drescue.model.CivilProtectionImpl
import it.unibo.drescue.utils.{Coordinator, CoordinatorCondition, CoordinatorImpl}

import scalafx.collections.ObservableBuffer

class ManageRescuesControllerImpl(private var mainController: MainControllerImpl,
                                  var rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.ManageRescue, this)

  var obsBuffer = new ObservableBuffer[EnrolledTeamInfo]()

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
    val task: Future[String] = mainController.pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
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
    println("&&" + cpIDList)

    //TODO start Ricart Agrawala's Algorithm to occupy the given rescueTeamID
    //Coordinator configuration
    val coordinator: Coordinator = CoordinatorImpl.getInstance()
    coordinator.setConnection(rabbitMQ)
    coordinator.setExchange(mainController.ExchangeName)
    coordinator.setCondition(CoordinatorCondition.WANTED)
    coordinator.setCsName(wantedRescueTeamID)
    coordinator.setMyID(mainController.model.cpID)
    coordinator.setReqTimestamp(new Timestamp(System.currentTimeMillis()))
    coordinator.createPendingCivilProtectionReplayStructure(cpIDList)

    //Broadcast request
    val reqCoordinationMessage: ReqCoordinationMessage = new messages.ReqCoordinationMessage()
    reqCoordinationMessage.setFrom(mainController.model.cpID)
    reqCoordinationMessage.setRescueTeamID(wantedRescueTeamID)
    reqCoordinationMessage.setTimestamp(coordinator.getReqTimestamp)

    rabbitMQ.sendMessage(mainController.ExchangeName, wantedRescueTeamID, null, reqCoordinationMessage)

    //The process is blocked as long as enter in cs (coordinator condition == HELD) or has received a negative responde (RS = OCCUPIED)
    while (coordinator.getCondition != CoordinatorCondition.HELD && coordinator.getCondition != CoordinatorCondition.DETACHED) {
      println(coordinator.getCondition)
    }
    if (coordinator.getCondition == CoordinatorCondition.HELD) {
      //TODO CS execution Update RT condition
      System.out.println("[CS Execution]")
      //the process came back from CS
      coordinator.backToCs()
    }

  }

  def stopPressed(wantedRescueTeamID: String) = {

    //TODO stop the given rescueTeamID, change local state of rescueTeamID in available

    //TODO send message to the other CP with in order to update their view

  }

  def backPress() = {
    mainController.changeView("Home")
  }

}
