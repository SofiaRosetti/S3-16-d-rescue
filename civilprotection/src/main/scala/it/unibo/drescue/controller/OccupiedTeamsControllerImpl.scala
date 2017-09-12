package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class OccupiedTeamsControllerImpl(private var model: List[ObjectModel],
                                  private var mainController: MainControllerImpl) {

  def stopRescuePress() = {
    mainController.changeView("Home")
  }

  def cancelPress() = {
    mainController.changeView("Home")
  }

}
