package it.unibo.drescue.controller

import scalafx.collections.ObservableBuffer

class HomeControllerImpl(private var mainController: MainControllerImpl) {

  //TODO start here a request for RequestCpAlertMsg(cpID)
  var obsBuffer = new ObservableBuffer[String]()

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

  def newRescuePress() = {
    mainController.changeView("NewRescue")
  }

  def newTeamPress() = {
    mainController.changeView("NewTeam")
  }

  def occupiedTeamsPress() = {
    mainController.changeView("OccupiedTeams")
  }

  def _obsBuffer = obsBuffer

  def refreshAlertsList() = {
    obsBuffer.insert(0, "new alert")
  }

}
