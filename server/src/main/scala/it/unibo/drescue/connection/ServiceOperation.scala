package it.unibo.drescue.connection

import com.rabbitmq.client.AMQP
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.database.DBConnection

/**
  * Trait modelling a general service to accessDB and handle the result.
  */
sealed trait ServiceOperation {

  /**
    * Access DB in order to perform the received request.
    *
    * @param jsonMessage string containing the message request
    * @return the result of the request as a Message
    */
  def accessDB(dBConnection: DBConnection, jsonMessage: String): Message

  /**
    * Handles DB result.
    *
    * @param rabbitMQ   connection to rabbitMQ
    * @param properties properties of the request
    * @param message    message to send as a response or forward
    */
  def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit

}

/**
  * Trait modelling a service that send response to requests.
  */
trait ServiceResponse extends ServiceOperation {

  override def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit = {
    val responseQueue: String = properties.getReplyTo
    rabbitMQ sendMessage("", responseQueue, null, message)
    println("[ServiceResponse] handleResult")
  }

}

/**
  * Trait modelling a service that forward requests.
  */
trait ServiceForward extends ServiceOperation {

  override def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit = {
    //TODO forward
    println("[ServiceForward] handleResult")
  }

}

/**
  * Trait modelling a service that send response or/and forward requests.
  */
trait ServiceResponseForward extends ServiceResponse with ServiceForward {

  override def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit = {
    super[ServiceResponse].handleDBresult(rabbitMQ, properties, message)
    super[ServiceForward].handleDBresult(rabbitMQ, properties, message)
    println("[ServiceResponseAndForward] handleResult")
  }

}

import it.unibo.drescue.communication.messages.response._

/**
  * Class that manage mobileuser messages related to authentication and
  * changes in profile.
  */
case class MobileuserService() extends ServiceResponseForward {

  override def accessDB(dBConnection: DBConnection, jsonMessage: String): Message = {

    val messageName: MessageType = MessageUtils.getMessageNameByJson(jsonMessage)

    messageName match {
      case MessageType.SIGN_UP_MESSAGE =>
        //TODO connect to DB and perform operations
        new SuccessfulMessageImpl
      case _ =>
        new ErrorMessageImpl("test")
    }

    //TODO add case for login, request profile (?), change password

  }

}