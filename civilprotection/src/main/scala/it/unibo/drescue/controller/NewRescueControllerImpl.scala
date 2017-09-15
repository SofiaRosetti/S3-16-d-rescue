package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class NewRescueControllerImpl(private var model: List[ObjectModel],
                              private var mainController: MainControllerImpl) {

  //TODO stopPress()
  //show a dialog "stopped as soon as possibile"
  //start thread with publish - subscribe request
  //change view to home

  def sendPress() = {
    mainController.changeView("Home")
    //TODO show dialog "waiting for approval"
    //TODO start thread with future
    //when future return
    // OK -> success dialog and update list
    // ERROR -> error dialog (it must choose another team)
  }

  def cancelPress() = {
    mainController.changeView("Home")
  }

}
