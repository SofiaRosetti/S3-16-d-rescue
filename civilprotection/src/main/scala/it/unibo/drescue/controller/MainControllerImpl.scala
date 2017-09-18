package it.unibo.drescue.controller

import java.util
import java.util.concurrent.{ExecutorService, Executors, Future}

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.response.{AlertsMessageImpl, ObjectModelMessageImpl}
import it.unibo.drescue.communication.messages.{Message, MessageType, MessageUtils, RequestCpAlertsMessageImpl}
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.localModel.{AlertEntry, CivilProtectionData}
import it.unibo.drescue.model.{AlertImpl, RescueTeamImpl, UpvotedAlertImpl}
import it.unibo.drescue.view.MainView

class MainControllerImpl(var model: CivilProtectionData, var rabbitMQ: RabbitMQImpl) {

  val pool: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors() + 1)
  var view = new MainView(null, null, null, null, null, null, null, null)

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def userLogged(username: String, rescueTeams: java.util.List[RescueTeamImpl]): Unit = {
    model.cpID = username // set cpID in main controller
    model.enrolledRescueTeams = rescueTeams

    //TODO start request
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
    //start thread consumer with runnable
    rabbitMQ.addConsumer(new AlertConsumer(rabbitMQ, this), model.cpID)

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

  def fromAlertImplToAlertEntry(alert: AlertImpl): AlertEntry
  = {
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


class AlertConsumer(private val rabbitMQ: RabbitMQImpl, private val mainController: MainControllerImpl)
  extends DefaultConsumer(rabbitMQ.getChannel) {
  override def handleDelivery(consumerTag: String,
                              envelope: Envelope,
                              properties: AMQP.BasicProperties,
                              body: Array[Byte]): Unit = {
    super.handleDelivery(consumerTag, envelope, properties, body)

    val message = new String(body, "UTF-8")
    println("[AlertConsumer] " + message)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(message)
    messageName match {
      case MessageType.OBJECT_MODEL_MESSAGE =>
        val objectModelMessage = GsonUtils.fromGson(message, classOf[ObjectModelMessageImpl])
        objectModelMessage.getObjectModel match {
          case alert: AlertImpl =>
          //TODO add to model.alertList
          case upvote: UpvotedAlertImpl =>
          //TODO change
          case _ => //Error
        }
      case _ => //do nothing
    }

  }

}

