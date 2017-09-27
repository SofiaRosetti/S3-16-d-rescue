package it.unibo.drescue.connection

import java.util.concurrent.{ArrayBlockingQueue, Callable}

import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.Message
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl
import org.slf4j.{Logger, LoggerFactory}

/**
  * Object companion of RequestHandler class
  */
object RequestHandler {
  private val ErrorServer = "Error during server communication."
}

/**
  * A class with the purpose of set up a RPC request
  *
  * @param rabbitMQ the request channel
  * @param message  the request message
  * @param queue    the request queue
  */
class RequestHandler(val rabbitMQ: RabbitMQImpl, val message: Message, val queue: QueueType) extends Callable[String] {

  private val Logger: Logger = LoggerFactory getLogger classOf[RequestHandler]

  override def call() = {
    var responseMessage: String = null
    try {
      val replyQueue = rabbitMQ.addReplyQueue()
      val props = rabbitMQ.setReplyTo(replyQueue)
      rabbitMQ.sendMessage("", queue.getQueueName, props, message)
      val response = new ArrayBlockingQueue[String](1)
      responseMessage = rabbitMQ.addRPCClientConsumer(response, replyQueue)
    } catch {
      case e: Exception => responseMessage = GsonUtils.toGson(new ErrorMessageImpl(RequestHandler.ErrorServer))
    }
    Logger info ("Handler Response: " + responseMessage)
    if (!StringUtils.isAValidString(responseMessage)) responseMessage = GsonUtils.toGson(new ErrorMessageImpl(RequestHandler.ErrorServer))

    responseMessage
  }

}
