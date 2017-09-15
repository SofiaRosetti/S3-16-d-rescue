package it.unibo.drescue.controller

import java.util.concurrent.{ExecutorService, Executors, Future}

import it.unibo.drescue.communication.messages._
import it.unibo.drescue.connection.{RabbitMQImpl, RequestHandler}
import it.unibo.drescue.model.ObjectModel

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

object LoginControllerImpl {
  val ErrorDialogTitle = "Login Error"
  val ErrorDialogHeader = "Empty login."
  val ErrorDialogContent = "Please, insert username and password."
}

class LoginControllerImpl(private var model: List[ObjectModel],
                          private var mainController: MainControllerImpl,
                          val channel: RabbitMQImpl
                         ) {

  val pool: ExecutorService = Executors.newFixedThreadPool(1)

  def loginPress(username: String, password: String) = {
    println(username)
    println(password)
    //TODO start info dialog (LOADING) without buttons
    val message: Message = CpLoginMessageImpl(username, password)
    val task: Future[String] = pool.submit(new RequestHandler(channel, message))
    val response: String = task.get()
    println("Login controller: " + response)
    val messageName: MessageType = MessageUtils.getMessageNameByJson(response)

    messageName match {

      case MessageType.RESCUE_TEAMS_MESSAGE => //TODO success
        //TODO stop dialog and change view
        //TODO set rescue teams list in main controller (with getter and setter)
        //TODO set cpID in main controller
        mainController.changeView("Home")
      case MessageType.ERROR_MESSAGE => //TODO show ERROR -> change dialog
    }

    pool.shutdown() //TODO verify
  }

  def emptyLogin() = {
    new Alert(AlertType.Error) {
      initOwner(mainController._view._stage)
      title = LoginControllerImpl.ErrorDialogTitle
      headerText = LoginControllerImpl.ErrorDialogHeader
      contentText = LoginControllerImpl.ErrorDialogContent
    }.showAndWait()
  }

}
