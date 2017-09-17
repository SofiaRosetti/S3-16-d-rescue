package it.unibo.drescue.controller

class NewRescueControllerImpl(private var mainController: MainControllerImpl) {

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
