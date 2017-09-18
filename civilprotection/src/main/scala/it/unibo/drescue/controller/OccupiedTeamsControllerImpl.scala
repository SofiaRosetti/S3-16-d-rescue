package it.unibo.drescue.controller

class OccupiedTeamsControllerImpl(private var mainController: MainControllerImpl) {

  def stopRescuePress() = {
    mainController.changeView("Home")
  }

  def cancelPress() = {
    mainController.changeView("Home")
  }

}
