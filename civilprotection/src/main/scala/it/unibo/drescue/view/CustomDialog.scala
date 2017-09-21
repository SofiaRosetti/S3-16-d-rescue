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

  val EnrollTeamError = "Enroll Team Error"
  val EnrollDialogHeader = "Something went wrong during team enrollment."
  val EnrollDialogContent = "Please complete all fields."
  val EnrollDialogAddressContent = "Please insert a valid address."
  val EnrollDialogIDContent = "The team ID is already existing."

  val OkDialogTitle = "Success"
  val OkDialogHeader = "All checks completed."
  val OkDialogContent = "Application is ready."

  val ProcessingDialogTitle = "Processing"
  val ProcessingDialogHeader = "All data ok."
  val ProcessingDialogContent = "The team is being adding."

  val AddedDialogHeader = "The team has been added successfully."
  val AddedDialogContent = "You can proceed now."

  val Processing = "Processing"
  val EmptyLogin = "EmptyLogin"
  val InfoLogin = "InfoLogin"
  val WrongLogin = "WrongLogin"
  val EmptyTeamData = "EmptyTeamData"
  val InvalidAddress = "InvalidAddress"
  val ExistingTeamID = "ExistingTeamID"
  val Ok = "Ok"
  val Added = "The team has been added."

  var dialog: Alert = _
}

class CustomDialog(mainController: MainControllerImpl) {


  def createDialog(dialogType: String): Alert = {
    dialogType match {
      case CustomDialog.EmptyLogin =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.LoginErrorDialogTitle
          headerText = CustomDialog.LoginErrorDialogHeader
          contentText = CustomDialog.LoginErrorDialogContent
        }
      case CustomDialog.InfoLogin =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.LoginErrorDialogTitle
          headerText = CustomDialog.LoginVerifyDialogHeader
          contentText = CustomDialog.LoginVerifyDialogContent
          buttonTypes.remove(0)
        }
      case CustomDialog.WrongLogin =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.LoginErrorDialogTitle
          headerText = CustomDialog.LoginWrongDialogHeader
          contentText = CustomDialog.LoginErrorDialogContent
        }
      case CustomDialog.EmptyTeamData =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.EnrollTeamError
          headerText = CustomDialog.EnrollDialogHeader
          contentText = CustomDialog.EnrollDialogContent
        }
      case CustomDialog.InvalidAddress =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.EnrollTeamError
          headerText = CustomDialog.EnrollDialogHeader
          contentText = CustomDialog.EnrollDialogAddressContent
        }
      case CustomDialog.ExistingTeamID =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.EnrollTeamError
          headerText = CustomDialog.EnrollDialogHeader
          contentText = CustomDialog.EnrollDialogIDContent
        }
      case CustomDialog.Ok =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.OkDialogTitle
          headerText = CustomDialog.OkDialogHeader
          contentText = CustomDialog.OkDialogContent
        }
      case CustomDialog.Processing =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.ProcessingDialogTitle
          headerText = CustomDialog.ProcessingDialogHeader
          contentText = CustomDialog.ProcessingDialogContent
          buttonTypes.remove(0)
        }
      case CustomDialog.Added =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.Ok
          headerText = CustomDialog.AddedDialogHeader
          contentText = CustomDialog.AddedDialogContent
        }
    }
    CustomDialog.dialog
  }

  def _dialog: Alert = CustomDialog.dialog

}
