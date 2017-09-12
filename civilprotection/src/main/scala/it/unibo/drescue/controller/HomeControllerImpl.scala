package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class HomeControllerImpl(private var model: List[ObjectModel],
                         private var mainController: MainControllerImpl) {

  def newRescuePress() = {
    mainController.changeView("NewRescue")
  }

  def newTeamPress() = {
    mainController.changeView("NewTeam")
  }

  def occupiedTeamsPress() = {
    mainController.changeView("OccupiedTeams")
  }

}
