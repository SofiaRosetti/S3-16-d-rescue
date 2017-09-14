package it.unibo.drescue.connection

import com.rabbitmq.client.AMQP
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.communication.messages.response.ObjectModelMessageImpl
import it.unibo.drescue.database.DBConnection
import it.unibo.drescue.database.exceptions._
import it.unibo.drescue.utils._

/**
  * Trait modelling a general service to accessDB and handle the result.
  */
sealed trait ServiceOperation {

  /**
    * Access DB in order to perform the received request.
    *
    * @param dbConnection connection to DB
    * @param jsonMessage  string containing the message request
    * @throws it.unibo.drescue.database.exceptions.DBConnectionException     if an error occur in DB connection
    * @throws it.unibo.drescue.database.exceptions.DBNotFoundRecordException if an error occur while searching an object
    * @throws it.unibo.drescue.database.exceptions.DBQueryException          if an error occur while executing a query
    * @throws it.unibo.drescue.utils.GeocodingException                      if an error occur while executing geocoding operations
    * @throws java.lang.Exception                                            if an unknown error occur
    * @return message to send as a response or forward
    */
  @throws(classOf[DBConnectionException])
  @throws(classOf[DBNotFoundRecordException])
  @throws(classOf[DBQueryException])
  @throws(classOf[GeocodingException])
  @throws(classOf[Exception])
  def accessDB(dbConnection: DBConnection, jsonMessage: String): Option[Message]

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
  }

}

/**
  * Trait modelling a service that forward requests.
  */
trait ServiceForward extends ServiceOperation {

  override def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit = {
    val forwardObjectMessage = message.asInstanceOf[ForwardObjectMessage]
    val objectModelMessage = new ObjectModelMessageImpl(forwardObjectMessage.objectModel)
    forwardObjectMessage.cpIDList foreach (cpID => {
      rabbitMQ sendMessage("", cpID, null, objectModelMessage)
    })
  }

}

/**
  * Trait modelling a service that send response or forward requests.
  */
trait ServiceResponseOrForward extends ServiceResponse with ServiceForward {

  override def handleDBresult(rabbitMQ: RabbitMQ, properties: AMQP.BasicProperties, message: Message): Unit = {
    val responseQueue: String = properties.getReplyTo
    if (responseQueue != null) {
      super[ServiceResponse].handleDBresult(rabbitMQ, properties, message)
    } else {
      super[ServiceForward].handleDBresult(rabbitMQ, properties, message)
    }
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
  private val findOneException = "Exception while trying to find user"
  private val inputError = "Incorrect input"
  private val wrongEmailOrPassword = "Wrong email and/or password."
}

/**
  * Class that manage mobileuser messages related to authentication and
  * changes in profile.
  */
case class MobileuserService() extends ServiceResponse {

  override def accessDB(dbConnection: DBConnection, jsonMessage: String): Option[Message] = {

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
            return Option(new ErrorMessageImpl(MobileuserService.duplicatedEmailMessage))
        }

        Option(new SuccessfulMessageImpl)

      case MessageType.LOGIN_MESSAGE =>

        val login = GsonUtils.fromGson(jsonMessage, classOf[LoginMessageImpl])
        val user = new UserImplBuilder()
          .setEmail(login.getEmail)
          .setPassword(login.getPassword)
          .createUserImpl()
        try {
          val userDao = (dbConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
          val userSelected = (userDao login user).asInstanceOf[User]
          val eventTypeDao = (dbConnection getDAO DBConnection.Table.EVENT_TYPE).asInstanceOf[EventTypeDao]
          val eventTypeList = eventTypeDao.findAll
          Option(new ResponseLoginMessageImpl(userSelected, eventTypeList))
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case notFound: DBNotFoundRecordException =>
            Option(new ErrorMessageImpl(MobileuserService.wrongEmailOrPassword))
        }

      case MessageType.CHANGE_PASSWORD_MESSAGE =>

        val changePassword = GsonUtils.fromGson(jsonMessage, classOf[ChangePasswordMessageImpl])
        val user = new UserImplBuilder()
          .setEmail(changePassword.getUserEmail)
          .setPassword(changePassword.getNewPassword)
          .createUserImpl()
        try {
          val userDao = (dbConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
          val userSelected = (userDao selectByIdentifier user).asInstanceOf[User]
          userSelected match {
            case null => throw new DBQueryException(MobileuserService.findOneException)
            case _ =>
              userSelected.getPassword match {
                case pass if pass == changePassword.getOldPassword =>
                  changePassword.getOldPassword match {
                    case password if password == changePassword.getNewPassword =>
                      Option(new ErrorMessageImpl(MobileuserService.inputError))
                    case _ =>
                      userDao update user
                      Option(new SuccessfulMessageImpl)
                  }
                case _ =>
                  Option(new ErrorMessageImpl(MobileuserService.inputError))
              }
          }
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
        }

      case MessageType.REQUEST_PROFILE_MESSAGE =>

        val profile = GsonUtils.fromGson(jsonMessage, classOf[RequestProfileMessageImpl])
        val user = new UserImplBuilder()
          .setEmail(profile.getUserEmail)
          .createUserImpl()
        try {
          val userDao = (dbConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
          val userSelected = (userDao selectByIdentifier user).asInstanceOf[User]
          userSelected match {
            case null => throw new DBQueryException(MobileuserService.findOneException)
            case _ => Option(new ProfileMessageImpl(userSelected))
          }
        } catch {
          case connection: DBConnectionException => throw connection
        }

      case _ => throw new Exception
    }

  }

}

import java.util

import scala.collection.mutable.ListBuffer

/**
  * Object companion of AlertsService case class.
  */
object AlertsService {

  private val NumberOfAlertsToGetFromApp: Int = 50

  /**
    * Gets the cpIDs of the given district.
    *
    * @param dbConnection connection to DB
    * @param district     district of which to find cp
    * @throws it.unibo.drescue.database.exceptions.DBConnectionException if an error occur in DB connection
    * @throws it.unibo.drescue.database.exceptions.DBQueryException      if an error occur while executing a query
    * @return a list containing all the cp that covers the given district
    */
  @throws(classOf[DBConnectionException])
  @throws(classOf[DBQueryException])
  def getCPofDistrict(dbConnection: DBConnection, district: String): ListBuffer[String] = {
    val cpAreaDao = (dbConnection getDAO DBConnection.Table.CP_AREA).asInstanceOf[CpAreaDao]
    val cpAreaList = cpAreaDao findCpAreasByDistrict district
    var cpIDList = new ListBuffer[String]
    cpAreaList forEach (cpArea => {
      val cpID = cpArea.getCpID
      cpIDList.append(cpID)
    })
    cpIDList
  }

  /**
    * Calculate the district using Geocoding classes.
    *
    * @param latitude
    * @param longitude
    * @throws it.unibo.drescue.utils.GeocodingException
    * @return the string representing the district
    */
  @throws(classOf[GeocodingException])
  def calculateDistrict(latitude: Double, longitude: Double): String = {
    new GeocodingImpl getDistrict(latitude, longitude)
  }
}

/**
  * Class that manage messages requests related to alerts both from
  * mobileuser and civil protection.
  */
case class AlertsService() extends ServiceResponseOrForward {

  override def accessDB(dbConnection: DBConnection, jsonMessage: String): Option[Message] = {

    val messageName = MessageUtils.getMessageNameByJson(jsonMessage)

    messageName match {

      case MessageType.NEW_ALERT_MESSAGE =>

        val newAlert = GsonUtils.fromGson(jsonMessage, classOf[NewAlertMessageImpl])

        var district: String = null
        try {
          district = AlertsService.calculateDistrict(newAlert.getLatitude, newAlert.getLongitude)
        } catch {
          case geocoding: GeocodingException => throw geocoding
        }

        try {
          val alertDao = (dbConnection getDAO DBConnection.Table.ALERT).asInstanceOf[AlertDao]
          val timestamp = alertDao.getCurrentTimestampForDb

          val alert = new AlertImplBuilder()
            .setTimestamp(timestamp)
            .setLatitude(newAlert.getLatitude)
            .setLongitude(newAlert.getLongitude)
            .setUserID(newAlert.getUserID)
            .setEventName(newAlert.getEventType)
            .setDistrictID(district)
            .setUpvotes(0)
            .createAlertImpl()

          val inseredAlert = (alertDao insertAndGet alert).asInstanceOf[Alert]

          val cpIDList = AlertsService.getCPofDistrict(dbConnection, district)

          Option(ForwardObjectMessage(cpIDList, inseredAlert))

        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case duplicated: DBDuplicatedRecordException => throw duplicated
          case notFound: DBNotFoundRecordException => throw notFound
        }

      case MessageType.REQUEST_UPVOTE_MESSAGE =>

        val upvotedAlert = GsonUtils.fromGson(jsonMessage, classOf[RequestUpvoteAlertMessageImpl])

        val upvote = new UpvotedAlertImpl(upvotedAlert.getUserID, upvotedAlert.getAlertID)

        try {
          //insert record into upvoted alert table
          val upvotedAlertDao = (dbConnection getDAO DBConnection.Table.UPVOTED_ALERT).asInstanceOf[UpvotedAlertDao]
          upvotedAlertDao insert upvote

          val alert = new AlertImplBuilder()
            .setAlertID(upvotedAlert.getAlertID)
            .createAlertImpl()

          //update number of upvotes in alert table
          val alertDao = (dbConnection getDAO DBConnection.Table.ALERT).asInstanceOf[AlertDao]
          alertDao update alert

          val cpIDList = AlertsService.getCPofDistrict(dbConnection, upvotedAlert.getDistrictID)

          Option(ForwardObjectMessage(cpIDList, upvote))

        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case duplicated: DBDuplicatedRecordException => throw duplicated
        }

      case MessageType.REQUEST_MOBILE_ALERTS_MESSAGE =>

        val mobileRequestAlerts = GsonUtils.fromGson(jsonMessage, classOf[RequestAlertsMessageImpl])

        var district: String = null
        try {
          district = AlertsService.calculateDistrict(mobileRequestAlerts.getLatitude, mobileRequestAlerts.getLongitude)
        } catch {
          case geocoding: GeocodingException => throw geocoding
        }

        try {
          val alertDao = (dbConnection getDAO DBConnection.Table.ALERT).asInstanceOf[AlertDao]
          val alertList: util.List[Alert] = alertDao findLast(AlertsService.NumberOfAlertsToGetFromApp, district)

          Option(new AlertsMessageImpl(alertList.asInstanceOf[util.List[AlertImpl]]))

        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
        }

      //TODO case alerts request from CP

      case _ => throw new Exception
    }
  }

}
