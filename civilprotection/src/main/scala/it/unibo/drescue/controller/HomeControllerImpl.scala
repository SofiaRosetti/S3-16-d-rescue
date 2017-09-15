package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

import scalafx.collections.ObservableBuffer

class HomeControllerImpl(private var model: List[ObjectModel],
                         private var mainController: MainControllerImpl) {

  var obsBuffer = new ObservableBuffer[String]()

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
