package it.unibo.drescue.connection

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.requests.{ChangePasswordMessageBuilderImpl, SignUpMessageBuilderImpl}
import it.unibo.drescue.communication.messages.requests.LoginMessageImpl
import it.unibo.drescue.communication.messages.{Message, MessageType}
import it.unibo.drescue.database.dao.UserDao
import it.unibo.drescue.database.{DBConnection, DBConnectionImpl}
import it.unibo.drescue.model.{User, UserImplBuilder}
import org.scalatest.FunSuite

class MobileuserServiceTest extends FunSuite {

  private val correctEmail: String = "j.doe@test.com"
  private val correctPassword: String = "test"
  private val incorrectEmail: String = "john.doe@test.com"
  private val incorrectPassword: String = "admin"
  private val userName: String = "John"
  private val userPhoneNumber: String = "3333333333"
  private val userSurname: String = "Doe"
  private val userTest: User = new UserImplBuilder()
    .setName(userName)
    .setSurname(userSurname)
    .setEmail(correctEmail)
    .setPassword(correctPassword)
    .setPhoneNumber(userPhoneNumber)
    .createUserImpl()


  var dBConnection: DBConnectionImpl = _

  def startCommunicationAndInsertUser(): UserDao = {
    startCommunication()
    insertUserIntoDb()
  }

  def startCommunication(): Unit = {
    dBConnection = DBConnectionImpl.getLocalConnection
  }

  def insertUserIntoDb(): UserDao = {
    val userDao = (dBConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
    userDao insert userTest
    userDao
  }

  def endSignUpCommunication(): Unit = {
    val userDao = (dBConnection getDAO DBConnection.Table.USER).asInstanceOf[UserDao]
    endCommunication(userDao)
  }

  def endCommunication(userDao: UserDao): Unit = {
    userDao delete userTest
    dBConnection closeConnection()
  }

  def signUpCommunication(): Message = {
    val messageSignUp: Message = new SignUpMessageBuilderImpl()
      .setName(userName)
      .setSurname(userSurname)
      .setEmail(correctEmail)
      .setPassword(correctPassword)
      .setPhoneNumber(userPhoneNumber)
      .build()
    mobileuserCommunication(messageSignUp)
  }

  def loginCommunication(email: String, password: String): Message = {
    val messageLogin: Message = new LoginMessageImpl(email, password)
    mobileuserCommunication(messageLogin)
  }

  def changePasswordCommunication(oldPassword: String, newPassword: String): Message = {
    val changePasswordMessage: Message = new ChangePasswordMessageBuilderImpl()
      .setUserEmail(correctEmail)
      .setOldPassword(oldPassword)
      .setNewPassword(newPassword)
      .build()
    mobileuserCommunication(changePasswordMessage)
  }

  def mobileuserCommunication(mobileuserMessage: Message): Message = {
    val message: String = GsonUtils toGson mobileuserMessage
    val service: ServiceOperation = new MobileuserService
    service.accessDB(dBConnection, message)
  }

  test("User should do correct sign up") {
    startCommunication()
    val responseMessage: Message = signUpCommunication()
    assert(responseMessage.getMessageType == MessageType.SUCCESSFUL_MESSAGE.getMessageType)
    endSignUpCommunication()
  }

  test("User should not do correct sign up for duplicate email") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = signUpCommunication()
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User with email '" + correctEmail + "' and password '"
    + correctPassword + "' should enter correct data for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = loginCommunication(correctEmail, correctPassword)
    assert(responseMessage.getMessageType == MessageType.RESPONSE_LOGIN_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User with email '" + correctEmail + "' and password '"
    + incorrectPassword + "' should not enter correct password for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = loginCommunication(correctEmail, incorrectPassword)
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User with email '" + incorrectEmail + "' and password '"
    + correctPassword + "' should not enter correct email for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = loginCommunication(incorrectEmail, correctPassword)
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User should be able to change password") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = changePasswordCommunication(correctPassword, incorrectPassword)
    assert(responseMessage.getMessageType == MessageType.SUCCESSFUL_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User should not be able to change password for incorrect old password") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = changePasswordCommunication(incorrectPassword, correctPassword)
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User should not be able to change password for incorrect new password") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = changePasswordCommunication(correctPassword, correctPassword)
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

}
