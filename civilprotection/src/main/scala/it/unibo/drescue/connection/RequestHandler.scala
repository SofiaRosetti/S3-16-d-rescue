package it.unibo.drescue.connection

import java.util.concurrent.{ArrayBlockingQueue, Callable}

import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.Message
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl

object RequestHandler {
  private val ErrorServer = "Error during server communication."
}

class RequestHandler(val rabbitMQ: RabbitMQImpl, val message: Message, val queue: QueueType) extends Callable[String] {


  override def call() = {
    var responseMessage: String = null
    //TODO request channel.send message and wait response
    try {
      val replyQueue = rabbitMQ.addReplyQueue(); //queue for response
      val props = rabbitMQ.setReplyTo(replyQueue);
      rabbitMQ.sendMessage("", queue.getQueueName, props, message)
      val response = new ArrayBlockingQueue[String](1)
      responseMessage = rabbitMQ.addRPCClientConsumer(response, replyQueue)
    } catch {
      case e: Exception => responseMessage = GsonUtils.toGson(new ErrorMessageImpl(RequestHandler.ErrorServer))
    }
    println("Handler Response: " + responseMessage)
    //if request reach timeout
    if (!StringUtils.isAValidString(responseMessage)) responseMessage = GsonUtils.toGson(new ErrorMessageImpl(RequestHandler.ErrorServer))

    responseMessage
  }

}
