package it.unibo.drescue.connection

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.requests.{ChangePasswordMessageBuilderImpl, SignUpMessageBuilderImpl}
import it.unibo.drescue.communication.messages.requests.{LoginMessageImpl, RequestProfileMessageImpl}
import it.unibo.drescue.communication.messages.{Message, MessageType}
import it.unibo.drescue.database.dao.UserDao
import it.unibo.drescue.database.exceptions.DBQueryException
import it.unibo.drescue.database.{DBConnection, DBConnectionImpl}
import it.unibo.drescue.model.{User, UserImplBuilder}
import org.scalatest.FunSuite

class MobileuserServiceTest extends FunSuite {

  private val CorrectEmail: String = "j.doe@test.com"
  private val CorrectPassword: String = "test"
  private val IncorrectEmail: String = "john.doe@test.com"
  private val IncorrectPassword: String = "admin"
  private val UserName: String = "John"
  private val UserPhoneNumber: String = "3333333333"
  private val UserSurname: String = "Doe"
  private val UserTest: User = new UserImplBuilder()
    .setName(UserName)
    .setSurname(UserSurname)
    .setEmail(CorrectEmail)
    .setPassword(CorrectPassword)
    .setPhoneNumber(UserPhoneNumber)
    .createUserImpl()


  private var dBConnection: DBConnectionImpl = _

  def startCommunicationAndInsertUser(): UserDao = {
    startCommunication()
    insertUserIntoDb()
  }

  def startCommunication(): Unit = {
    dBConnection = DBConnectionImpl.getLocalConnection
  }

  def insertUserIntoDb(): UserDao = {
    val userDao = (dBConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
    userDao insert UserTest
    userDao
  }

  def endSignUpCommunication(): Unit = {
    val userDao = (dBConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
    endCommunication(userDao)
  }

  def endCommunication(userDao: UserDao): Unit = {
    userDao delete UserTest
    dBConnection closeConnection()
  }

  def signUpCommunication(): Option[Message] = {
    val messageSignUp: Message = new SignUpMessageBuilderImpl()
      .setName(UserName)
      .setSurname(UserSurname)
      .setEmail(CorrectEmail)
      .setPassword(CorrectPassword)
      .setPhoneNumber(UserPhoneNumber)
      .build()
    mobileuserCommunication(messageSignUp)
  }

  def mobileuserCommunication(mobileuserMessage: Message): Option[Message] = {
    val message: String = GsonUtils toGson mobileuserMessage
    val service: ServiceOperation = new MobileuserService
    service.accessDB(dBConnection, message)
  }

  def loginCommunication(email: String, password: String): Option[Message] = {
    val messageLogin: Message = new LoginMessageImpl(email, password)
    mobileuserCommunication(messageLogin)
  }

  def changePasswordCommunication(oldPassword: String, newPassword: String): Option[Message] = {
    val changePasswordMessage: Message = new ChangePasswordMessageBuilderImpl()
      .setUserEmail(CorrectEmail)
      .setOldPassword(oldPassword)
      .setNewPassword(newPassword)
      .build()
    mobileuserCommunication(changePasswordMessage)
  }

  test("User should do correct sign up") {
    startCommunication()
    val responseMessage: Option[Message] = signUpCommunication()
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.SUCCESSFUL_MESSAGE.getMessageType)
      case None => fail()
    }
    endSignUpCommunication()
  }

  test("User should not do correct sign up for duplicate email") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = signUpCommunication()
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User with email '" + CorrectEmail + "' and password '"
    + CorrectPassword + "' should enter correct data for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = loginCommunication(CorrectEmail, CorrectPassword)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.RESPONSE_LOGIN_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User with email '" + CorrectEmail + "' and password '"
    + IncorrectPassword + "' should not enter correct password for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = loginCommunication(CorrectEmail, IncorrectPassword)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User with email '" + IncorrectEmail + "' and password '"
    + CorrectPassword + "' should not enter correct email for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = loginCommunication(IncorrectEmail, CorrectPassword)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User should be able to change password") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = changePasswordCommunication(CorrectPassword, IncorrectPassword)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.SUCCESSFUL_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User should not be able to change password for incorrect old password") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = changePasswordCommunication(IncorrectPassword, CorrectPassword)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User should not be able to change password for incorrect new password") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Option[Message] = changePasswordCommunication(CorrectPassword, CorrectPassword)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("User should get his profile") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val profileMessage: Message = new RequestProfileMessageImpl(CorrectEmail)
    val responseMessage: Option[Message] = mobileuserCommunication(profileMessage)
    responseMessage match {
      case Some(responseMessage) =>
        assert(responseMessage.getMessageType == MessageType.PROFILE_MESSAGE.getMessageType)
      case None => fail()
    }
    endCommunication(userDao)
  }

  test("Profile request with wrong email should produce DBQueryException") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val profileMessage: Message = new RequestProfileMessageImpl(IncorrectEmail)
    assertThrows[DBQueryException] {
      mobileuserCommunication(profileMessage)
    }
    endCommunication(userDao)
  }

}
