package it.unibo.drescue.view

import it.unibo.drescue.controller.MainControllerImpl

import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Object companion of CustomDialog class
  */
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

  val TeamNotified = "The rescue team has been notified."
  val TeamWorking = "The rescue team is already occupied."
  val TeamAvailable = "The rescue team is available"
  val ChooseAnotherTeam = "Please choose another team."

  val SelectAlertHeader = "It is necessary to select an alert to start a rescue."
  val SelectAlertContent = "Please select one."

  val CpNotAuthorized = "You don't have the authorization"
  val CpNotAuthorizedToStop = "The rescue team was occupied by another civil protection"

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
  val Sent = "Team notified"
  val Stop = "Team stop"
  val TeamNotSent = "Team working"
  val TeamStopNotAuthorized: String = "Stop not authorized"
  val SelectAlert = "Select Alert"

  var dialog: Alert = _
  var errorMsg: String = _
}

/**
  * A class that creates custom dialogs to be shown in civil protection views
  *
  * @param mainController the main controller
  */
class CustomDialog(mainController: MainControllerImpl) {

  /**
    * Sets a custom error text
    *
    * @param text the error text
    */
  def setErrorText(text: String): Unit = {
    CustomDialog.errorMsg = text
  }


  /**
    * Creates a custom dialog depending on the type parameter
    *
    * @param dialogType the dialog type
    * @return an alert that composes the custom dialog
    */
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
          title = CustomDialog.ProcessingDialogTitle
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
      case CustomDialog.Sent =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.Sent
          headerText = CustomDialog.TeamNotified
          contentText = CustomDialog.AddedDialogContent
        }
      case CustomDialog.Stop =>
        CustomDialog.dialog = new Alert(AlertType.Information) {
          initOwner(mainController._view._stage)
          title = CustomDialog.Stop
          headerText = CustomDialog.TeamNotified
          contentText = CustomDialog.TeamAvailable
        }
      case CustomDialog.TeamNotSent =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.ErrorDialogTitle
          headerText = CustomDialog.TeamWorking
          contentText = CustomDialog.ChooseAnotherTeam
        }
      case CustomDialog.TeamStopNotAuthorized =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.ErrorDialogTitle
          headerText = CustomDialog.CpNotAuthorized
          contentText = CustomDialog.CpNotAuthorizedToStop
        }
      case CustomDialog.SelectAlert =>
        CustomDialog.dialog = new Alert(AlertType.Error) {
          initOwner(mainController._view._stage)
          title = CustomDialog.SelectAlert
          headerText = CustomDialog.SelectAlertHeader
          contentText = CustomDialog.SelectAlertContent
        }
      case _ => println("other match")
    }
    CustomDialog.dialog
  }

  /**
    * @return the created custom dialog
    */
  def _dialog: Alert = CustomDialog.dialog

}
