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

  val CheckingDialogTitle = "Checking"
  val CheckingDialogHeader = "Checking inserted data."
  val CheckingDialogContent = "Please wait until process is finished."

  val ErrorDialogTitle = "Error"
  val ErrorDialogHeader = "Something went wrong."
  val ErrorDialogContent = "An error occurred."

  val EnrollOK = "The team has been successfully enrolled."

  val SelectTeamContent = "Please select a rescue team."

  val Processing = "Processing"
  val EmptyLogin = "EmptyLogin"
  val InfoLogin = "InfoLogin"
  val WrongLogin = "WrongLogin"
  val EmptyTeamData = "EmptyTeamData"
  val InvalidAddress = "InvalidAddress"
  val ExistingTeamID = "ExistingTeamID"
  val Ok = "Ok"
  val Added = "The team has been added."
  val Checking = "Checking"
  val Error = "Error"
  val SelectTeam = "Nothing selected"

  var dialog: Alert = _
  var errorMsg: String = _
}

class CustomDialog(mainController: MainControllerImpl) {

  def setErrorText(text: String): Unit = {
    CustomDialog.errorMsg = text
  }


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
      case CustomDialog.Checking =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.CheckingDialogTitle
          headerText = CustomDialog.CheckingDialogHeader
          contentText = CustomDialog.CheckingDialogContent
          buttonTypes.remove(0)
        }
      case CustomDialog.Error =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.ErrorDialogTitle
          if (CustomDialog.errorMsg != null) {
            headerText = CustomDialog.ErrorDialogContent
            contentText = CustomDialog.errorMsg
          } else {
            headerText = CustomDialog.ErrorDialogHeader
            contentText = CustomDialog.ErrorDialogContent
          }
        }
      case CustomDialog.EnrollOK =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.Ok
          headerText = CustomDialog.EnrollOK
          contentText = CustomDialog.AddedDialogContent
        }
      case CustomDialog.SelectTeam =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.ErrorDialogTitle
          headerText = CustomDialog.SelectTeam
          contentText = CustomDialog.SelectTeamContent
        }
    }
    CustomDialog.dialog
  }

  def _dialog: Alert = CustomDialog.dialog

}
