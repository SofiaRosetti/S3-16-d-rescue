package it.unibo.drescue.connection

import java.util.concurrent.{ArrayBlockingQueue, Callable}

import it.unibo.drescue.StringUtils
import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.Message
import it.unibo.drescue.communication.messages.response.ErrorMessageImpl

object RequestHandler {
  private val ErrorServer = "Error during server communication."
}

class RequestHandler(val channel: RabbitMQImpl, val message: Message) extends Callable[String] {


  override def call() = {
    var responseMessage: String = null
    //TODO request channel.send message and wait response
    try {
      val replyQueue = channel.addReplyQueue(); //queue for response
      val props = channel.setReplyTo(replyQueue);
      channel.sendMessage("", QueueType.CIVIL_PROTECTION_QUEUE.getQueueName, props, message)
      val response = new ArrayBlockingQueue[String](1)
      responseMessage = channel.addRPCClientConsumer(response, replyQueue)
    } catch {
      case e: Exception => responseMessage = GsonUtils.toGson(new ErrorMessageImpl(RequestHandler.ErrorServer))
    }
    println("Handler Response: " + responseMessage)
    //if request reach timeout
    if (!StringUtils.isAValidString(responseMessage)) responseMessage = GsonUtils.toGson(new ErrorMessageImpl(RequestHandler.ErrorServer))

    responseMessage
  }

}
