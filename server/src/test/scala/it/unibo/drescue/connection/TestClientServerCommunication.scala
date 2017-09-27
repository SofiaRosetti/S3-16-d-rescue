package it.unibo.drescue.connection

import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}

import com.rabbitmq.client.{AMQP, DefaultConsumer}
import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.database.DBConnection
import it.unibo.drescue.model.AlertImplBuilder
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.collection.mutable.ListBuffer

class TestClientServerCommunication extends FunSuite with BeforeAndAfter {

  val serverQueue = "fake_receive_queue"
  val clientForwardQueue = "fake_forward_queue"
  var connection: RabbitMQConnection = _

  before {
    connection = new RabbitMQConnectionImpl("localhost")
    connection.openConnection()
    Service(connection, serverQueue, FakeService())
  }

  after {
    connection.closeConnection()
  }

  test("Test RPC") {

    val rabbitMQ = new RabbitMQImpl(connection)

    val replyQueue: String = rabbitMQ addReplyQueue()
    val props: AMQP.BasicProperties = rabbitMQ setReplyTo replyQueue

    rabbitMQ sendMessage("", serverQueue, props, FakeRPCMessage())

    val blockingQueue: BlockingQueue[String] = new ArrayBlockingQueue[String](1)
    val responseMessage = rabbitMQ addRPCClientConsumer(blockingQueue, replyQueue)

    if (StringUtils isAValidString responseMessage) {
      val messageName = MessageUtils getMessageNameByJson responseMessage
      messageName match {
        case MessageType.FAKE_RPC_MESSAGE =>
        case _ => fail
      }
    }

  }

  test("Test publish/subscribe") {

    val rabbitMQ = new RabbitMQImpl(connection)

    FakeForwardClient(connection)

    rabbitMQ sendMessage("", serverQueue, null, FakeForwardMessage())

  }

  private case class FakeRPCMessage()
    extends AbstractMessage(MessageType.FAKE_RPC_MESSAGE) {
  }

  private case class FakeForwardMessage()
    extends AbstractMessage(MessageType.FAKE_FORWARD_MESSAGE) {
  }

  private case class FakeService() extends ServiceResponseOrForward {

    override def accessDB(dbConnection: DBConnection, jsonMessage: String): Option[Message] = {

      val messageName = MessageUtils.getMessageNameByJson(jsonMessage)

      messageName match {
        case MessageType.FAKE_RPC_MESSAGE => Option(FakeRPCMessage())
        case MessageType.FAKE_FORWARD_MESSAGE =>
          val fakeListID = new ListBuffer[String]
          fakeListID.append(clientForwardQueue)
          val alert = new AlertImplBuilder().createAlertImpl()
          Option(ForwardObjectMessage(fakeListID, alert)) //send an alert as example
        case _ => None
      }
    }
  }

  private case class FakeForwardClient(connection: RabbitMQConnection) {

    val rabbitMQ = new RabbitMQImpl(connection)
    rabbitMQ addQueue clientForwardQueue
    rabbitMQ addConsumer(new DefaultConsumer(rabbitMQ getChannel()) {

      override def handleDelivery(consumerTag: _root_.java.lang.String,
                                  envelope: _root_.com.rabbitmq.client.Envelope,
                                  properties: _root_.com.rabbitmq.client.AMQP.BasicProperties,
                                  body: Array[Byte]): Unit = {
        super.handleDelivery(consumerTag, envelope, properties, body)

        val message = new String(body, "UTF-8")
        val messageName = MessageUtils.getMessageNameByJson(message)
        messageName match {
          case MessageType.FORWARD_ALERT_MESSAGE =>
          case _ => fail
        }

      }
    }, clientForwardQueue)
  }

}
