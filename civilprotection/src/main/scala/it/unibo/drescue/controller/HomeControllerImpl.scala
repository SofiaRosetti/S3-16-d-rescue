package it.unibo.drescue.controller

import it.unibo.drescue.localModel.{AlertEntry, Observers}
import it.unibo.drescue.view.CustomDialog

import scalafx.collections.ObservableBuffer

class HomeControllerImpl(private var mainController: MainControllerImpl) extends Observer {

  mainController.model.addObserver(Observers.Home, this)

  var obsBuffer = new ObservableBuffer[AlertEntry]()

  def manageRescuesPress() = {
    mainController.sendOrStop = "Stop"
    mainController.alertInManage = null
    mainController.changeView("ManageRescues")
  }

  def startRescuePress(alert: AlertEntry) = {
    mainController.alertInManage = alert
    mainController.sendOrStop = "Send"
    mainController.changeView("ManageRescues")
  }

  def startSelectAlertDialog() = {
    val dialog = new CustomDialog(mainController).createDialog(CustomDialog.SelectAlert)
    dialog.showAndWait()
  }

  def enrollTeamPress() = {
    mainController.initializeNotEnrolled()
    mainController.changeView("NewTeam")
  }

  override def onReceivingNotification(): Unit = {
    obsBuffer.clear()
    mainController.model.lastAlerts.forEach(
      (alertEntry: AlertEntry) => {
        obsBuffer add alertEntry
      }
    )
  }

}
