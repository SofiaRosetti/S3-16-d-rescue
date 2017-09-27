package it.unibo.drescue.connection

import com.rabbitmq.client.{AMQP, DefaultConsumer, Envelope}
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.controller.MainControllerImpl
import it.unibo.drescue.localModel.AlertEntry
import it.unibo.drescue.view.CustomDialog
import org.slf4j.{Logger, LoggerFactory}

/**
  * A class representing a consumer with the purpose of
  * consume messages containing new alerts and alert upvotes
  *
  * @param rabbitMQ           the channel
  * @param mainControllerImpl the main controller
  */
case class AlertConsumer(private val rabbitMQ: RabbitMQ,
                         private val mainControllerImpl: MainControllerImpl)
  extends DefaultConsumer(rabbitMQ.getChannel) {

  private val Logger: Logger = LoggerFactory getLogger classOf[AlertConsumer]

  override def handleDelivery(consumerTag: String,
                              envelope: Envelope,
                              properties: AMQP.BasicProperties,
                              body: Array[Byte]): Unit = {

    super.handleDelivery(consumerTag, envelope, properties, body)

    val message = new String(body, "UTF-8")
    Logger info ("[AlertConsumer] " + message)

    val messageName: MessageType = MessageUtils.getMessageNameByJson(message)
    messageName match {

      case MessageType.FORWARD_ALERT_MESSAGE =>
        val alertMessage: ForwardAlertMessage = GsonUtils.fromGson(message, classOf[ForwardAlertMessage])
        val newAlertEntry = mainControllerImpl.fromAlertImplToAlertEntry(alertMessage.alert)
        val list = mainControllerImpl.model.lastAlerts
        list.add(0, newAlertEntry)
        mainControllerImpl.model.lastAlerts = list

      case MessageType.FORWARD_UPVOTED_ALERT_MESSAGE =>
        val upvotedAlertMessage: ForwardUpvotedAlertMessage = GsonUtils.fromGson(message, classOf[ForwardUpvotedAlertMessage])
        val alertID = upvotedAlertMessage.upvotedAlert.getAlertID

        var indexToChange: Int = -1
        mainControllerImpl.model.lastAlerts forEach ((alert: AlertEntry) => {
          val valueID = alert.alertID.value
          if (valueID == alertID.toString) {
            Logger info ("Equals to " + alert.alertID.value)
            indexToChange = mainControllerImpl.model.lastAlerts.indexOf(alert)
          }
        })
        val list = mainControllerImpl.model.lastAlerts
        if (indexToChange != -1) {

          val alertEntry = list.remove(indexToChange)
          val newEntry = new AlertEntry(
            alertEntry._alertID,
            alertEntry._timestamp,
            alertEntry._latitude,
            alertEntry._longitude,
            alertEntry._userID,
            alertEntry._eventName,
            alertEntry._districtID,
            alertEntry._upvotes + 1)
          list.add(0, newEntry)
          mainControllerImpl.model.lastAlerts = list
        }

      case _ =>
        val dialog = new CustomDialog(mainControllerImpl).createDialog(CustomDialog.Error)
        dialog.showAndWait()
    }
  }
}