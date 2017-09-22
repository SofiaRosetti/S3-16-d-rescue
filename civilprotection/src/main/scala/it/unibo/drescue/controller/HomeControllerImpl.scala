package it.unibo.drescue.controller

import it.unibo.drescue.localModel.{AlertEntry, Observers}
import it.unibo.drescue.view.CustomDialog

import scalafx.collections.ObservableBuffer

class HomeControllerImpl(private var mainController: MainControllerImpl) extends Observer {

  mainController.model.addObserver(Observers.Home, this)

  var obsBuffer = new ObservableBuffer[AlertEntry]()

  def manageRescuesPress() = {
    //TODO
    //when pressed -> change view to manage rescues WITHOUT alert params
    mainController.changeView("ManageRescues")
  }

  def startRescuePress(alert: AlertEntry) = {
    println(alert)
    //TODO
    // start rescue button inactive until alert (from list view selected)
    // when pressed -> change view to manage rescue WITH alert params
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
