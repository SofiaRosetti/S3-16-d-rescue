package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.communication.messages.{CpLoginMessageImpl, Message, MessageType, MessageUtils}
import it.unibo.drescue.connection.{RabbitMQImpl, RequestHandler}
import it.unibo.drescue.model.ObjectModel
import it.unibo.drescue.view.CustomDialog

import scalafx.scene.control.Alert

object LoginControllerImpl {

  val Login = "Login"
  val Home = "Home"

  val EmptyLogin = "EmptyLogin"
  val InfoLogin = "InfoLogin"
  val WrongLogin = "WrongLogin"
}

class LoginControllerImpl(private var model: List[ObjectModel],
                          private var mainController: MainControllerImpl,
                          val channel: RabbitMQImpl
                         ) {

  val pool: ExecutorService = Executors.newFixedThreadPool(1)
  var dialog: Alert = null

  def loginPress(username: String, password: String) = {
    startLoadingDialog()

    println(username)
    println(password)

    val message: Message = CpLoginMessageImpl(username, password)
    val task: Future[String] = pool.submit(new RequestHandler(channel, message))
    val response: String = task.get()
    println("Login controller: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

    messageName match {

      case MessageType.RESCUE_TEAMS_MESSAGE => { //TODO success
        //TODO set rescue teams list in main controller (with getter and setter)
        mainController.changeView(LoginControllerImpl.Home) // stop dialog and change view
        mainController.cpID_(username) // set cpID in main controller
      }
      case MessageType.ERROR_MESSAGE => {
        startWrongLoginDialog() // show ERROR -> change dialog
      }
    }

    //pool.shutdown() //TODO verify

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
