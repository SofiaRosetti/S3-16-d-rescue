package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.communication.GsonUtils
import it.unibo.drescue.communication.messages._
import it.unibo.drescue.connection.{QueueType, RabbitMQImpl, RequestHandler}
import it.unibo.drescue.view.CustomDialog

import scalafx.scene.control.Alert

object LoginControllerImpl {

  val Login = "Login"
  val Home = "Home"

  val EmptyLogin = "EmptyLogin"
  val InfoLogin = "InfoLogin"
  val WrongLogin = "WrongLogin"
}

class LoginControllerImpl(private var mainController: MainControllerImpl,
                          val rabbitMQ: RabbitMQImpl
                         ) {


  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  var dialog: Alert = _

  def loginPress(username: String, password: String) = {
    startLoadingDialog()

    println(username)
    println(password)

    val message: Message = CpLoginMessageImpl(username, password)
    val task: Future[String] = pool.submit(new RequestHandler(rabbitMQ, message, QueueType.CIVIL_PROTECTION_QUEUE))
    val response: String = task.get()
    println("Login controller: " + response)
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

  def startLoadingDialog() = {
    dialog = new CustomDialog(mainController).createDialog(LoginControllerImpl.InfoLogin)
    dialog.show()
  }

  def startWrongLoginDialog() = {
    mainController.changeView(LoginControllerImpl.Login)
    dialog = new CustomDialog(mainController).createDialog(LoginControllerImpl.WrongLogin)
    dialog.showAndWait()
  }

  def startEmptyLoginDialog() = {
    dialog = new CustomDialog(mainController).createDialog(LoginControllerImpl.EmptyLogin)
    dialog.showAndWait()
  }

}
