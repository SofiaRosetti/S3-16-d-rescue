package it.unibo.drescue.controller

import it.unibo.drescue.localModel.{AlertEntry, Observers}

import scalafx.collections.ObservableBuffer

class HomeControllerImpl(private var mainController: MainControllerImpl) extends Observer {

  mainController.model.addObserver(Observers.Home, this)

  var obsBuffer = new ObservableBuffer[AlertEntry]()

  //TODO
  // - listView with alerts updated by a thread consumer
  // - enrollTeam button -> change view
  // - startRescue button ->
  //      - inactive until alert (from list view) selected
  //      - when pressed -> change view to manage rescue with alert params
  // - manageRescues button -> change view to manage rescues without alert params

  def startAlertsRequest() = {
    // TODO request for alerts with RequestCpAlertMsg(cpID)
  }

  def manageRescuesPress() = {
    mainController.changeView("ManageRescues")
  }

  def startRescuePress() = {
    mainController.changeView("ManageRescues")
  }

  def enrollTeamPress() = {
    mainController.initializeNotEnrolled()
    mainController.changeView("NewTeam")
  }

  /*def newRescuePress() = {
    mainController.changeView("NewRescue")
  }

  def newTeamPress() = {
    mainController.initializeNotEnrolled()
    mainController.changeView("NewTeam")
  }

  def occupiedTeamsPress() = {
    mainController.changeView("OccupiedTeams")
  }

  def refreshAlertsList() = {
    obsBuffer.insert(0, "new alert")
  }*/

  /**
    * TODO
    */
  override def onReceivingNotification(): Unit = {
    obsBuffer.clear()
    mainController.model.lastAlerts.forEach(
      (alertEntry: AlertEntry) => {
        obsBuffer add alertEntry
      }
    )
  }

}
