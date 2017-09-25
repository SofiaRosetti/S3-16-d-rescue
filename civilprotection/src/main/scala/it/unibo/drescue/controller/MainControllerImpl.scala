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
    * @return the button text that has to be active
    */
  def sendOrStop = _sendOrStop

  /**
    * Sets to value the button text that has to be active
    *
    * @param value
    */
  def sendOrStop_=(value: String): Unit = {
    _sendOrStop = value
  }

  /**
    * @return the AlertEntry corresponding to the selected alert in home view
    */
  def alertInManage = _alertInManage

  /**
    * Sets to value the AlertEntry corresponding to the selected alert in home view
    *
    * @param value
    */
  def alertInManage_=(value: AlertEntry): Unit = {
    _alertInManage = value
  }

  /**
    * Adds the main view to the controller
    *
    * @param viewValue the main view to be added
    */
  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  /**
    * Creates consumers to retrieve alerts and rescue teams after login
    *
    * @param username    the civil protection ID
    * @param rescueTeams the rescue teams list returned from the login
    */
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

  /**
    * Initializes the local model alerts list
    *
    * @param list the alerts list local model has to be initialized to
    */
  def initializeAlerts(list: java.util.List[AlertImpl]): Unit = {
    model.lastAlerts = fromAlertImplListToAlertEntryList(list)
  }

  /**
    * Converts a list of AlertImpl to a list of AlertEntry
    *
    * @param list the list to be converted
    * @return the converted list
    */
  def fromAlertImplListToAlertEntryList(list: java.util.List[AlertImpl]): java.util.List[AlertEntry] = {
    val entryList: java.util.List[AlertEntry] = new util.ArrayList[AlertEntry]()
    list.forEach((alert: AlertImpl) => {
      entryList.add(fromAlertImplToAlertEntry(alert))
    })
    entryList
  }

  /**
    * Converts an AlertImpl to an AlertEntry
    *
    * @param alert the AlertImpl to be converted
    * @return the converted AlertEntry
    */
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

  /**
    * Initializes the local model EnrolledTeamInfo list
    *
    * @param list the list local model has to be initialized to
    */
  def initializeEnrolledTeamInfoList(list: java.util.List[RescueTeamImpl]): Unit = {
    model.enrolledTeamInfoList = fromRescueTeamListToEnrolledTeamInfoList(list)
  }

  /**
    * Converts a RescueTeam list to a EnrolledTeamInfo list
    *
    * @param list the list to be converted
    * @return the converted list
    */
  def fromRescueTeamListToEnrolledTeamInfoList(list: java.util.List[RescueTeamImpl]): java.util.List[EnrolledTeamInfo] = {
    val entryList: java.util.List[EnrolledTeamInfo] = new util.ArrayList[EnrolledTeamInfo]()
    list.forEach((rescueTeam: RescueTeamImpl) => {
      entryList.add(fromRescueTeamToEnrolledTeamInfoEntry(rescueTeam))
    })
    entryList
  }

  /**
    * Converts a RescueTeam to a EnrollTeamInfo
    * @param rescueTeam the RescueTeam to be converted
    * @return the converted EnrolledTeamInfo
    */
  def fromRescueTeamToEnrolledTeamInfoEntry(rescueTeam: RescueTeamImpl): EnrolledTeamInfo = {
    new EnrolledTeamInfo(
      rescueTeam.getRescueTeamID,
      rescueTeam.getName,
      rescueTeam.getPhoneNumber,
      true,
      "",
      "")
  }

  /**
    * Starts a custom error dialog
    */
  def startErrorDialog() = {
    val dialog = new CustomDialog(this).createDialog(CustomDialog.Error)
    dialog.showAndWait()
  }

  /**
    * Initializes the local model not enrolled teams list with a DB query result message
    */
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

  /**
    * Initializes the local model not enrolled teams
    * @param list the list local model has to be initialized to
    */
  def initializeNotEnrolledModel(list: java.util.List[RescueTeamImpl]) = {
    model.notEnrolledRescueTeams = list
  }

  /**
    * Changes the view to the next view
    * @param nextView the next view
    */
  def changeView(nextView: String) = {
    view.changeView(nextView)
  }

  /**
    * @return the main view
    */
  def _view: MainView = view

}