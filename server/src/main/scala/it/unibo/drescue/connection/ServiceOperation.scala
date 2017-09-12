package it.unibo.drescue.connection

import com.rabbitmq.client.AMQP
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.database.DBConnection
import it.unibo.drescue.database.exceptions._
import it.unibo.drescue.utils.GeocodingException

/**
  * Trait modelling a general service to accessDB and handle the result.
  */
sealed trait ServiceOperation {

  /**
    * Access DB in order to perform the received request.
    *
    * @param dBConnection string containing the message request
    * @param jsonMessage  the result of the request as a Message
    * @throws it.unibo.drescue.database.exceptions.DBConnectionException     if an error occur in DB connection
    * @throws it.unibo.drescue.database.exceptions.DBNotFoundRecordException if an error occur while searching an object
    * @throws it.unibo.drescue.database.exceptions.DBQueryException          if an error occur while executing a query
    * @throws it.unibo.drescue.utils.GeocodingException                      if an error occur while executing geocoding operations
    * @return message to send as a response or forward
    */
  @throws(classOf[DBConnectionException])
  @throws(classOf[DBNotFoundRecordException])
  @throws(classOf[DBQueryException])
  @throws(classOf[GeocodingException])
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

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.requests._
import it.unibo.drescue.communication.messages.response._
import it.unibo.drescue.database.dao._
import it.unibo.drescue.model._

/**
  * Object companion of MobileuserService case class.
  */
object MobileuserService {
  private val duplicatedEmailMessage = "Duplicated email."
}

/**
  * Class that manage mobileuser messages related to authentication and
  * changes in profile.
  */
case class MobileuserService() extends ServiceResponse {

  override def accessDB(dbConnection: DBConnection, jsonMessage: String): Message = {

    val messageName: MessageType = MessageUtils.getMessageNameByJson(jsonMessage)

    messageName match {
        
      case MessageType.SIGN_UP_MESSAGE =>

        val signUp = GsonUtils.fromGson(jsonMessage, classOf[SignUpMessageImpl])

        val user = new UserImplBuilder()
          .setName(signUp.getName)
          .setSurname(signUp.getSurname)
          .setEmail(signUp.getEmail)
          .setPhoneNumber(signUp.getPhoneNumber)
          .setPassword(signUp.getPassword)
          .createUserImpl()

        try {
          val userDao = (dbConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
          userDao insert user
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case duplicated: DBDuplicatedRecordException =>
            return new ErrorMessageImpl(MobileuserService.duplicatedEmailMessage)
        }

        new SuccessfulMessageImpl

      case _ => throw new Exception
    }

    //TODO add case for login, request profile (?), change password

  }

}