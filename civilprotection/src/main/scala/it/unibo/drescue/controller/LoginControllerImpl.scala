package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

object LoginControllerImpl {
  val ErrorDialogTitle = "Login Error"
  val ErrorDialogHeader = "Empty login."
  val ErrorDialogContent = "Please, insert username and password."
}

class LoginControllerImpl(private var model: List[ObjectModel],
                          private var mainController: MainControllerImpl) {

  def loginPress(username: String, password: String) = {
    println(username)
    println(password)
    mainController.changeView("Home")
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
