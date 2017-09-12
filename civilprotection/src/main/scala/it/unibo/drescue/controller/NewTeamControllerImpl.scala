package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class NewTeamControllerImpl(private var model: List[ObjectModel],
                            private var mainController: MainControllerImpl) {

  def selectPress() = {
    mainController.changeView("Home")
  }

  def cancelPress() = {
    mainController.changeView("Home")
  }

}
