package it.unibo.drescue.controller

import java.util
import java.util.concurrent.{ExecutorService, Executors, Future}

import com.rabbitmq.client.BuiltinExchangeType
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.ReqRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.communication.messages.response.AlertsMessageImpl
import it.unibo.drescue.connection._
import it.unibo.drescue.localModel.{AlertEntry, CivilProtectionData, EnrolledTeamInfo}
import it.unibo.drescue.model.{AlertImpl, RescueTeamImpl}
import it.unibo.drescue.view.{CustomDialog, MainView}

class MainControllerImpl(var model: CivilProtectionData, val rabbitMQ: RabbitMQImpl) {

  val ExchangeName = "rt_exchange"

  val pool: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors() + 1)
  var view = new MainView(null, null, null, null, null, null)
  var queueName: String = _
  private var _sendOrStop: String = "Stop"
  private var _alertInManage: AlertEntry = _

  /**
    *
    * @return the button text that has to be active
    */
  def sendOrStop = _sendOrStop

  def sendOrStop_=(value: String): Unit = {
    _sendOrStop = value
  }

  def alertInManage = _alertInManage

  def alertInManage_=(value: AlertEntry): Unit = {
    _alertInManage = value
  }

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def userLogged(username: String, rescueTeams: java.util.List[RescueTeamImpl]): Unit = {
    model.cpID = username
    model.enrolledRescueTeams = rescueTeams

    initializeEnrolledTeamInfoList(rescueTeams)
    println(model.enrolledTeamInfoList)

    rabbitMQ declareExchange(ExchangeName, BuiltinExchangeType.DIRECT)
    queueName = rabbitMQ addReplyQueue()
    rabbitMQ bindQueueToExchange(queueName, ExchangeName, rescueTeams)
    val cpConsumer: RescueTeamConsumer = RescueTeamConsumer(rabbitMQ, this)
    rabbitMQ addConsumer(cpConsumer, queueName)

    rescueTeams forEach (rescueTeam => {
      val rescueTeamConditionMessage = new ReqRescueTeamConditionMessageBuilderImpl()
        .setRescueTeamID(rescueTeam.getRescueTeamID)
        .setFrom(username)
        .build()
      rabbitMQ.sendMessage(ExchangeName, rescueTeam.getRescueTeamID, null, rescueTeamConditionMessage)
    })

    val message: Message = RequestCpAlertsMessageImpl(model.cpID)
    val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.ALERTS_QUEUE))
    val response: String = task.get()
    println("Main controller - alerts: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)
    messageName match {
      case MessageType.ALERTS_MESSAGE =>
        val alertsMessage = GsonUtils.fromGson(response, classOf[AlertsMessageImpl])
        initializeAlerts(alertsMessage.getAlerts)
      case _ => startErrorDialog()
    }

    rabbitMQ.addQueue(model.cpID)
    rabbitMQ addConsumer(AlertConsumer(rabbitMQ, this), model.cpID)
  }

  def startErrorDialog() = {
    val dialog = new CustomDialog(this).createDialog(CustomDialog.Error)
    dialog.showAndWait()
  }

  def initializeAlerts(list: java.util.List[AlertImpl]): Unit = {
    model.lastAlerts = fromAlertImplListToAlertEntryList(list)
  }

  def fromAlertImplListToAlertEntryList(list: java.util.List[AlertImpl]): java.util.List[AlertEntry] = {
    val entryList: java.util.List[AlertEntry] = new util.ArrayList[AlertEntry]()
    list.forEach((alert: AlertImpl) => {
      entryList.add(fromAlertImplToAlertEntry(alert))
    })
    entryList
  }

  def fromAlertImplToAlertEntry(alert: AlertImpl): AlertEntry = {
    new AlertEntry(
      alert.getAlertID,
      alert.getTimestamp,
      alert.getLatitude,
      alert.getLongitude,
      alert.getUserID,
      alert.getEventName,
      alert.getDistrictID,
      alert.getUpvotes)
  }

  def initializeEnrolledTeamInfoList(list: java.util.List[RescueTeamImpl]): Unit = {
    model.enrolledTeamInfoList = fromRescueTeamListToEnrolledTeamInfoList(list)
  }

  def fromRescueTeamListToEnrolledTeamInfoList(list: java.util.List[RescueTeamImpl]): java.util.List[EnrolledTeamInfo] = {
    val entryList: java.util.List[EnrolledTeamInfo] = new util.ArrayList[EnrolledTeamInfo]()
    list.forEach((rescueTeam: RescueTeamImpl) => {
      entryList.add(fromRescueTeamToEnrolledTeamInfoEntry(rescueTeam))
    })
    entryList
  }

  def fromRescueTeamToEnrolledTeamInfoEntry(rescueTeam: RescueTeamImpl): EnrolledTeamInfo = {
    new EnrolledTeamInfo(
      rescueTeam.getRescueTeamID,
      rescueTeam.getName,
      rescueTeam.getPhoneNumber,
      true,
      "",
      "")
  }

  def initializeNotEnrolled(): Unit = {
    val message: Message = GetRescueTeamsNotEnrolledMessageImpl(model.cpID)
    val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
    val response: String = task.get()
    println("Main controller - initialize not enrolled rescue team: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)
    messageName match {
      case MessageType.RESCUE_TEAMS_MESSAGE =>
        val teamsMessage = GsonUtils.fromGson(response, classOf[RescueTeamsMessageImpl])
        initializeNotEnrolledModel(teamsMessage.rescueTeamsList)
      case _ => startErrorDialog()
    }
  }

  def initializeNotEnrolledModel(list: java.util.List[RescueTeamImpl]) = {
    model.notEnrolledRescueTeams = list
  }

  def changeView(nextView: String) = {
    view.changeView(nextView)
  }

  def _view: MainView = view

}