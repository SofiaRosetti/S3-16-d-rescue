package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.view.CustomDialog
import org.slf4j.{Logger, LoggerFactory}

import scalafx.scene.control.Alert

/**
  * Companion object of LoginControllerImpl class
  */
object LoginControllerImpl {

  val Login = "Login"
  val Home = "Home"

  val EmptyLogin = "EmptyLogin"
  val InfoLogin = "InfoLogin"
  val WrongLogin = "WrongLogin"
}

/**
  * A class representing the login controller
  *
  * @param mainController the main controller
  * @param rabbitMQ       the channel used to handle requests and responses
  */
class LoginControllerImpl(private var mainController: MainControllerImpl,
                          val rabbitMQ: RabbitMQImpl) {

  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  private val Logger: Logger = LoggerFactory getLogger classOf[LoginControllerImpl]
  var dialog: Alert = _

  /**
    * Performs actions to verify if login credentials are correct
    * and changes the view to home view
    *
    * @param username the inserted username
    * @param password the inserted password
    */
  def loginPress(username: String, password: String) = {
    startLoadingDialog()

    Logger info ("Username: " + username + ", Password: " + password)

    val message: Message = CpLoginMessageImpl(username, password)
    val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
    val response: String = task.get()
    Logger info ("Login controller: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

    messageName match {

      case MessageType.RESCUE_TEAMS_MESSAGE =>
        val responseMessage: RescueTeamsMessageImpl = GsonUtils.fromGson(response, classOf[RescueTeamsMessageImpl])
        mainController.userLogged(username, responseMessage.rescueTeamsList)
        mainController.changeView(LoginControllerImpl.Home)
      case MessageType.ERROR_MESSAGE =>
        startWrongLoginDialog()
      case _ => //do nothing
    }
  }

  /**
    * Starts a custom dialog that tells the user to wait until credentials verify is completed
    */
  def startLoadingDialog() = {
    dialog = new CustomDialog(mainController).createDialog(LoginControllerImpl.InfoLogin)
    dialog.show()
  }

  /**
    * Starts a custom dialog which informs the user that the inserted credentials are incorrect
    */
  def startWrongLoginDialog() = {
    mainController.changeView(LoginControllerImpl.Login)
    dialog = new CustomDialog(mainController).createDialog(LoginControllerImpl.WrongLogin)
    dialog.showAndWait()
  }

  /**
    * Starts a custom dialog which tells the user to fill username and password
    */
  def startEmptyLoginDialog() = {
    dialog = new CustomDialog(mainController).createDialog(LoginControllerImpl.EmptyLogin)
    dialog.showAndWait()
  }

}
