package it.unibo.drescue.controller

import com.rabbitmq.client.AMQP
import it.unibo.drescue.connection.{RabbitMQConnectionImpl, RabbitMQImpl}
import it.unibo.drescue.model.ObjectModel
import it.unibo.drescue.view.MainView

class MainControllerImpl(private var model: List[ObjectModel]) {

  var connection: RabbitMQConnectionImpl = null
  var rabbitMQ: RabbitMQImpl = null

  var view = new MainView(null, null, null, null, null, null)

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def setupRabbitConnection(): Boolean = {
    try {
      connection = new RabbitMQConnectionImpl("localhost")
      connection.openConnection()

      rabbitMQ = new RabbitMQImpl(connection)
      val replyQueue: String = rabbitMQ.addReplyQueue()
      val props: AMQP.BasicProperties = rabbitMQ.setReplyTo(replyQueue)
    } catch {
      case e: Exception => // TODO handle exception
    } finally {
      if (connection != null) {
        connection.closeConnection()
      }
    }
    true
  }

  def changeView(nextView: String) = {
    view.changeView(nextView)
  }
}
