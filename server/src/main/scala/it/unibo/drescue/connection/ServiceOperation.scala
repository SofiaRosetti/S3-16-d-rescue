package it.unibo.drescue.connection

import com.rabbitmq.client.AMQP
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.communication.messages.response.ObjectModelMessageImpl
import it.unibo.drescue.database.DBConnection
import it.unibo.drescue.database.exceptions._
import it.unibo.drescue.geocoding.{GeocodingException, GeocodingImpl}

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
    * @throws it.unibo.drescue.geocoding.GeocodingException                  if an error occur while executing geocoding operations
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
    val forwardObjectMessageType = MessageType.FORWARD_MESSAGE.getMessageType
    message.getMessageType match {
      case `forwardObjectMessageType` =>
        val forwardObjectMessage = message.asInstanceOf[ForwardObjectMessage]
        val objectModelMessage = new ObjectModelMessageImpl(forwardObjectMessage.objectModel)
        forwardObjectMessage.cpIDList foreach (cpID => {
          rabbitMQ sendMessage("", cpID, null, objectModelMessage)
        })
      case _ => //do nothing
    }
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

import java.util

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages.requests._
import it.unibo.drescue.communication.messages.response._
import it.unibo.drescue.database.dao._
import it.unibo.drescue.model._

/**
  * Object companion of MobileuserService case class.
  */
object MobileuserService {
  private val DuplicatedEmailMessage: String = "Duplicated email."
  private val FindOneException: String = "Exception while trying to find user"
  private val InputError: String = "Incorrect input"
  private val WrongEmailOrPassword: String = "Wrong email and/or password."
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
          Option(new SuccessfulMessageImpl)
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case duplicated: DBDuplicatedRecordException =>
            Option(new ErrorMessageImpl(MobileuserService.DuplicatedEmailMessage))
        }

      case MessageType.LOGIN_MESSAGE =>

        val login = GsonUtils.fromGson(jsonMessage, classOf[LoginMessageImpl])
        val user = new UserImplBuilder()
          .setEmail(login.getEmail)
          .setPassword(login.getPassword)
          .createUserImpl()
        try {
          val userDao = (dbConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
          val userSelected = (userDao login user).asInstanceOf[UserImpl]
          val eventTypeDao = (dbConnection getDAO DBConnection.Table.EVENT_TYPE).asInstanceOf[EventTypeDao]
          val eventTypeList = eventTypeDao.findAll
          Option(new ResponseLoginMessageImpl(userSelected, eventTypeList.asInstanceOf[util.List[EventTypeImpl]]))
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case notFound: DBNotFoundRecordException =>
            Option(new ErrorMessageImpl(MobileuserService.WrongEmailOrPassword))
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
            case null => throw new DBQueryException(MobileuserService.FindOneException)
            case _ =>
              userSelected.getPassword match {
                case pass if pass == changePassword.getOldPassword =>
                  changePassword.getOldPassword match {
                    case password if password == changePassword.getNewPassword =>
                      Option(new ErrorMessageImpl(MobileuserService.InputError))
                    case _ =>
                      userDao update user
                      Option(new SuccessfulMessageImpl)
                  }
                case _ =>
                  Option(new ErrorMessageImpl(MobileuserService.InputError))
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
            case null => throw new DBQueryException(MobileuserService.FindOneException)
            case _ => Option(new ProfileMessageImpl(userSelected))
          }
        } catch {
          case connection: DBConnectionException => throw connection
        }

      case _ => throw new Exception
    }

  }

}

import scala.collection.mutable.ListBuffer

/**
  * Object companion of AlertsService case class.
  */
object AlertsService {

  private val NumberOfAlertsToGet: Int = 50

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
    * @param latitude  latitude
    * @param longitude longitude
    * @throws it.unibo.drescue.geocoding.GeocodingException if an error occur while executing geocoding operations
    * @return the string representing the district
    */
  @throws(classOf[GeocodingException])
  def calculateDistrict(latitude: Double, longitude: Double): String = {
    new GeocodingImpl getDistrict(latitude, longitude)
  }

  /**
    * Gets alerts of the given district.
    *
    * @param alertDao DAO of alert
    * @param district district of which to find the alerts
    * @throws it.unibo.drescue.database.exceptions.DBQueryException if an error occur while executing a query
    * @return a java.util.List containing the alerts
    */
  @throws(classOf[DBQueryException])
  def findAlertsOfDistrict(alertDao: AlertDao, district: String): util.List[Alert] = {
    alertDao findLast(AlertsService.NumberOfAlertsToGet, district)
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
          val alertList: util.List[Alert] = AlertsService.findAlertsOfDistrict(alertDao, district)

          Option(new AlertsMessageImpl(alertList.asInstanceOf[util.List[AlertImpl]]))

        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
        }

      case MessageType.REQUEST_CP_ALERTS_MESSAGE =>

        val cpRequestAlerts = GsonUtils.fromGson(jsonMessage, classOf[RequestCpAlertsMessageImpl])

        try {
          val cpAreaDao = (dbConnection getDAO DBConnection.Table.CP_AREA).asInstanceOf[CpAreaDao]
          val cpAreaList = cpAreaDao findCpAreasByCp cpRequestAlerts.cpID
          var cpDistrictList = new ListBuffer[String]
          cpAreaList forEach (cpArea => {
            val districtID = cpArea.getDistrictID
            cpDistrictList.append(districtID)
          })

          val alertDao = (dbConnection getDAO DBConnection.Table.ALERT).asInstanceOf[AlertDao]
          var alertList: util.List[Alert] = new util.ArrayList[Alert]
          cpDistrictList.foreach(districtID => {
            alertList addAll AlertsService.findAlertsOfDistrict(alertDao, districtID)
          })

          Option(new AlertsMessageImpl(alertList.asInstanceOf[util.List[AlertImpl]]))

        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
        }

      case _ => throw new Exception
    }
  }

}

object CivilProtectionService {
  private val WrongCpIdOrPassword = "Wrong cpID and/or password."
  private val DuplicatedTeamIDMessage: String = "Duplicated teamID."
  private val DuplicatedEnrollmentMessage: String = "Duplicated enrollment."
}

/**
  * Class that manage messages requests related to civil protection
  */
case class CivilProtectionService() extends ServiceResponseOrForward {

  override def accessDB(dbConnection: DBConnection, jsonMessage: String): Option[Message] = {

    val messageName = MessageUtils.getMessageNameByJson(jsonMessage)

    messageName match {

      case MessageType.CP_LOGIN_MESSAGE =>
        val login = GsonUtils.fromGson(jsonMessage, classOf[CpLoginMessageImpl])
        val cp = new CivilProtectionImpl(login.cpID, login.password)
        try {
          val cpDao = (dbConnection getDAO DBConnection.Table.CIVIL_PROTECTION).asInstanceOf[CivilProtectionDao]
          val cpSelected = (cpDao login cp).asInstanceOf[CivilProtection]
          val cpEnrollmentDao = (dbConnection getDAO DBConnection.Table.CP_ENROLLMENT).asInstanceOf[CpEnrollmentDao]
          val rescueTeamsList = cpEnrollmentDao.findAllRescueTeamGivenACp(cp.getCpID, true)
          Option(new RescueTeamsMessageImpl(rescueTeamsList.asInstanceOf[util.List[RescueTeamImpl]]))
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case notFound: DBNotFoundRecordException =>
            Option(new ErrorMessageImpl(CivilProtectionService.WrongCpIdOrPassword))
        }

      case MessageType.RESCUE_TEAMS_NOT_ENROLLED_MESSAGE =>
        val message = GsonUtils.fromGson(jsonMessage, classOf[GetRescueTeamsNotEnrolledMessageImpl])
        try {
          val cpEnrollmentDao = (dbConnection getDAO DBConnection.Table.CP_ENROLLMENT).asInstanceOf[CpEnrollmentDao]
          val rescueTeamsList = cpEnrollmentDao.findAllRescueTeamGivenACp(message.cpID, false)
          Option(new RescueTeamsMessageImpl(rescueTeamsList.asInstanceOf[util.List[RescueTeamImpl]]))
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
        }

      case MessageType.ADD_RESCUE_TEAM_MESSAGE =>
        val message = GsonUtils.fromGson(jsonMessage, classOf[NewRescueTeamMessage])
        try {
          val rescueTeamDao = (dbConnection getDAO DBConnection.Table.RESCUE_TEAM).asInstanceOf[RescueTeamDao]
          val rescueTeam = new RescueTeamImplBuilder()
            .setRescueTeamID(message.rescueTeamID)
            .setName(message.rescueTeamName)
            .setLatitude(message.rescueTeamLatitude)
            .setLongitude(message.rescueTeamLongitude)
            .setPassword(message.rescueTeamPassword)
            .setPhoneNumber(message.rescueTeamPhoneNumber)
            .createRescueTeamImpl()
          rescueTeamDao insert rescueTeam
          Option(new SuccessfulMessageImpl())
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case duplicated: DBDuplicatedRecordException =>
            Option(new ErrorMessageImpl(CivilProtectionService.DuplicatedTeamIDMessage))
        }
      case MessageType.ENROLL_RESCUE_TEAM_MESSAGE =>
        val message = GsonUtils.fromGson(jsonMessage, classOf[EnrollRescueTeamMessageImpl])
        try {
          val cpEnrollmentDao = (dbConnection getDAO DBConnection.Table.CP_ENROLLMENT).asInstanceOf[CpEnrollmentDao]
          val cpEnrollment = new CpEnrollmentImpl(message.cpID, message.rescueTeamID)
          cpEnrollmentDao insert cpEnrollment
          Option(new SuccessfulMessageImpl())
        } catch {
          case connection: DBConnectionException => throw connection
          case query: DBQueryException => throw query
          case duplicated: DBDuplicatedRecordException =>
            Option(new ErrorMessageImpl(CivilProtectionService.DuplicatedEnrollmentMessage))
        }

      case _ => throw new Exception
    }
  }
}