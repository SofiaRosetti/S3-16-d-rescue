package it.unibo.drescue.controller

import java.util
import java.util.concurrent.{ExecutorService, Executors, Future}

import com.rabbitmq.client.BuiltinExchangeType
import it.unibo.drescue.communication.builder.ReqRescueTeamConditionMessageBuilderImpl
import it.unibo.drescue.communication.{CPConsumer, GsonUtils}
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.communication.messages.response.AlertsMessageImpl
import it.unibo.drescue.connection._
import it.unibo.drescue.localModel.{AlertEntry, CivilProtectionData}
import it.unibo.drescue.model.{AlertImpl, RescueTeamImpl}
import it.unibo.drescue.view.MainView

class MainControllerImpl(var model: CivilProtectionData, val rabbitMQ: RabbitMQImpl) {

  val ExchangeName = "rt_exchange"

  val pool: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors() + 1)
  var view = new MainView(null, null, null, null, null, null, null, null)

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def userLogged(username: String, rescueTeams: java.util.List[RescueTeamImpl]): Unit = {
    model.cpID = username // set cpID in main controller
    model.enrolledRescueTeams = rescueTeams

    rabbitMQ declareExchange(ExchangeName, BuiltinExchangeType.DIRECT)
    val queueName = rabbitMQ addReplyQueue()
    rabbitMQ bindQueueToExchange(queueName, ExchangeName, rescueTeams)
    val cpConsumer : RescueTeamConsumer = RescueTeamConsumer(rabbitMQ, this)
    rabbitMQ addConsumer(cpConsumer, queueName)
    //ask for availability

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
      case _ => //TODO error
    }

    rabbitMQ.addQueue(model.cpID)
    rabbitMQ addConsumer(AlertConsumer(rabbitMQ, this), model.cpID)

    //TODO request availability on each rescue team queue

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

  def changeView(nextView: String) = {
    view.changeView(nextView)
  }

  def _view: MainView = view

}