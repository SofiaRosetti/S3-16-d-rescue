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
  val Stop: String = "Team stop"
  val StopNotAuthorized: String = "Stop not authorized"
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

  def sendPressed(wantedRescueTeamID: String, alertID: String) = {
    if (wantedRescueTeamID != ""){
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
        //TODO dialog message già occupato
        print("RT Già occupata da me")
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
        println("associated cp" + cpIDList)

        if (cpIDList.size() > 0){
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

            criticalSectionExecution(alertID, rescueTeamToChange, indexToChange)

            //TODO send a message to inform other cp
            sendReplyRescueTeamCondition(wantedRescueTeamID, RescueTeamCondition.OCCUPIED)

            //the process came back from CS
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



  def stopPressed(wantedRescueTeamID: String) = {
    var indexToChange: Int = -1
    var rescueTeamToChange: EnrolledTeamInfo = null
    val list = mainController.model.enrolledTeamInfoList
    list forEach ((rescueTeam: EnrolledTeamInfo) => {
      if (rescueTeam.teamID.value == wantedRescueTeamID) {
        indexToChange = list.indexOf(rescueTeam)
        rescueTeamToChange = list.get(indexToChange)
      }
    })
    print("cp Id: " + rescueTeamToChange.cpID.value);
    if (rescueTeamToChange.cpID.value != ""){
      if (rescueTeamToChange.cpID.value == mainController.model.cpID) {
        mainController.model.modifyEnrollment(indexToChange, new EnrolledTeamInfo(
          rescueTeamToChange.teamID.value,
          rescueTeamToChange.teamName.value,
          rescueTeamToChange.phoneNumber.value,
          true,
          "",
          "")
        )

        //send message to the other CP with in order to update their view
        sendReplyRescueTeamCondition(wantedRescueTeamID, RescueTeamCondition.AVAILABLE)
        stopSuccessDialog()

      } else {
        //TODO error message
        print("Stop not authorized")
        stopNotAuthorizedErrorDialog()
      }
    }
    else {
      //TODO messaggio che non c'è nulla da stoppare
      print("Nessun RT da fermare" );
    }
  }

  def startSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Sent)
    dialog.showAndWait()
  }

  def stopSuccessDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Stop)
    dialog.showAndWait()
  }

  def startErrorDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.Error)
    dialog.showAndWait()
  }

  def stopNotAuthorizedErrorDialog() = {
    dialog = new CustomDialog(mainController).createDialog(ManageRescuesControllerImpl.StopNotAuthorized)
    dialog.showAndWait()
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


  def criticalSectionExecution(alertID: String, rescueTeamToChange: EnrolledTeamInfo, indexToChange: Integer) = {
    println("[CS Execution]")
    //TODO get alert ID
    mainController.model.modifyEnrollment(indexToChange, new EnrolledTeamInfo(
      rescueTeamToChange.teamID.value,
      rescueTeamToChange.teamName.value,
      rescueTeamToChange.phoneNumber.value,
      false,
      mainController.model.cpID,
      alertID)
    )
  }

}
