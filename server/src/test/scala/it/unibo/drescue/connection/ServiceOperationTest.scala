package it.unibo.drescue.connection

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.builder.requests.SignUpMessageBuilderImpl
import it.unibo.drescue.communication.messages.requests.LoginMessageImpl
import it.unibo.drescue.communication.messages.{Message, MessageType}
import it.unibo.drescue.database.dao.UserDao
import it.unibo.drescue.database.{DBConnection, DBConnectionImpl}
import it.unibo.drescue.model.{User, UserImplBuilder}
import org.scalatest.FunSuite

class ServiceOperationTest extends FunSuite {

  val correctEmail: String = "j.doe@test.com"
  val correctPassword: String = "test"
  val incorrectEmail: String = "john.doe@test.com"
  val incorrectPassword: String = "admin"
  val userName: String = "John"
  val userPhoneNumber: String = "3333333333"
  val userSurname: String = "Doe"
  val userTest: User = new UserImplBuilder()
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

  test("User with email '" + correctEmail + "' and password '" + correctPassword + "' should enter correct data for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = loginCommunication(correctEmail, correctPassword)
    assert(responseMessage.getMessageType == MessageType.RESPONSE_LOGIN_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User with email '" + correctEmail + "' and password '" + incorrectPassword + "' should not enter correct password for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = loginCommunication(correctEmail, incorrectPassword)
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

  test("User with email '" + incorrectEmail + "' and password '" + correctPassword + "' should not enter correct email for login") {
    val userDao: UserDao = startCommunicationAndInsertUser()
    val responseMessage: Message = loginCommunication(incorrectEmail, correctPassword)
    assert(responseMessage.getMessageType == MessageType.ERROR_MESSAGE.getMessageType)
    endCommunication(userDao)
  }

}
