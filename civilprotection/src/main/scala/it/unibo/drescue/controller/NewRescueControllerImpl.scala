package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class NewRescueControllerImpl(private var model: List[ObjectModel],
                              private var mainController: MainControllerImpl) {

  def sendPress() = {
    mainController.changeView("Home")
  }

  def cancelPress() = {
    mainController.changeView("Home")
  }

}
