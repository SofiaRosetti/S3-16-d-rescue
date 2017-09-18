package it.unibo.drescue.controller

import java.util

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.response.{AlertsMessageImpl, ObjectModelMessageImpl}
import it.unibo.drescue.communication.messages.{MessageType, MessageUtils}
import it.unibo.drescue.connection.RabbitMQ
import it.unibo.drescue.localModel.{AlertEntry, CivilProtectionData}
import it.unibo.drescue.model.{AlertImpl, UpvotedAlertImpl}
import it.unibo.drescue.view.MainView

class MainControllerImpl(var model: CivilProtectionData) {

  var view = new MainView(null, null, null, null, null, null, null)

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def changeView(nextView: String) = {
    view.changeView(nextView)
  }

  def _view: MainView = view

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

}


class AlertConsumer(private val rabbitMQ: RabbitMQ, private val mainController: MainControllerImpl) extends DefaultConsumer(rabbitMQ.getChannel) {
  override def handleDelivery(consumerTag: String,
                              envelope: Envelope,
                              properties: AMQP.BasicProperties,
                              body: Array[Byte]): Unit = {
    super.handleDelivery(consumerTag, envelope, properties, body)

    val message = new String(body, "UTF-8")
    println("[AlertConsumer] " + message)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(message)
    messageName match {
      case MessageType.ALERTS_MESSAGE =>
        val alertsMessage = GsonUtils.fromGson(message, classOf[AlertsMessageImpl])
        mainController.initializeAlerts(alertsMessage.getAlerts)

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
