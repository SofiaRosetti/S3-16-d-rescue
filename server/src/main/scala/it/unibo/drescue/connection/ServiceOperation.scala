package it.unibo.drescue.connection

import com.rabbitmq.client.AMQP
import it.unibo.drescue.communication.messages.{Message, MessageUtils}

/**
  * Trait modelling operations to accessDB and handle the result.
  */
sealed trait ServiceOperation {

  /**
    * Access DB in order to perform the received request.
    *
    * @param jsonMessage string containing the message request
    * @return the result of the request as a Message
    */
  def accessDB(jsonMessage: String): Message

  /**
    * Handles DB result and send response/forward requests basing on
    * the given properties parameter containing properties of the request.
    * If properties.getReplyTo is not null then the client needs a response
    * otherwise the message has to be forwarded.
    *
    * @param rabbitMQ   connection to rabbitMQ
    * @param properties properties of the request
    * @param message    message to send as a response or forward
    */
  def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit = {
    val responseQueue: String = properties.getReplyTo
    if (responseQueue != null) { //RPC
      rabbitMQ sendMessage("", responseQueue, null, message)
    } else { //forward message
      //TODO
    }
  }

}

/**
  * Class that manage mobileuser messages related to authentication and
  * changes in profile.
  */
case class MobileuserServiceOperation() extends ServiceOperation {

  override def accessDB(jsonMessage: String): Message = {

    import it.unibo.drescue.StringUtils
    import it.unibo.drescue.communication.messages.MessageType
    import it.unibo.drescue.communication.messages.response._

    val messageType: String = StringUtils getMessageType jsonMessage
    val messageName: MessageType = MessageUtils.getMessageNameByType(messageType)

    messageName match {
      case MessageType.SIGN_UP_MESSAGE =>
        //TODO getDAO
        new SuccessfulMessageImpl
      case _ =>
        new ErrorMessageImpl("test") //TODO check because isn't working
    }


    //TODO add case for login, request profile (?), change password

  }

}