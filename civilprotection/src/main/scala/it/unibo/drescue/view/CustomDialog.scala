package it.unibo.drescue.view

import it.unibo.drescue.controller.MainControllerImpl

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

object CustomDialog {
  val LoginErrorDialogTitle = "Login Error"
  val LoginErrorDialogHeader = "Empty login."
  val LoginErrorDialogContent = "Please insert correct username and password."
  val LoginVerifyDialogTitle = "Loading"
  val LoginVerifyDialogHeader = "Credentials being verified."
  val LoginVerifyDialogContent = "Please wait until verify is completed."
  val LoginWrongDialogHeader = "Wrong credentials."

  val EmptyLogin = "EmptyLogin"
  val InfoLogin = "InfoLogin"
  val WrongLogin = "WrongLogin"

  var dialog: Alert = null
}

class CustomDialog(mainController: MainControllerImpl) {


  def createDialog(dialogType: String): Alert = {
    dialogType match {
      case CustomDialog.EmptyLogin => {
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.LoginErrorDialogTitle
          headerText = CustomDialog.LoginErrorDialogHeader
          contentText = CustomDialog.LoginErrorDialogContent
        }
      }
      case CustomDialog.InfoLogin => {
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.LoginErrorDialogTitle
          headerText = CustomDialog.LoginVerifyDialogHeader
          contentText = CustomDialog.LoginVerifyDialogContent
          buttonTypes.remove(0)
        }
      }
      case CustomDialog.WrongLogin => {
        CustomDialog.dialog.close()
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.LoginErrorDialogTitle
          headerText = CustomDialog.LoginWrongDialogHeader
          contentText = CustomDialog.LoginErrorDialogContent
        }
      }
    }
    CustomDialog.dialog
  }

  def _dialog: Alert = CustomDialog.dialog

}
