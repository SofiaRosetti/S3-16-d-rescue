package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class EnrollTeamControllerImpl(private var model: List[ObjectModel],
                               private var mainController: MainControllerImpl) {
  
  //TODO start here a request for GetAllRescueTeam

  def addPress(): Unit = ???

  //TODO addPress()
  //check Input insert
  //start thread with future (insertAndGet)
  //when future returns
  // OK -> add the return to combobox
  //ERROR -> dialog

  def selectPress() = {
    mainController.changeView("Home")
    //TODO start thread with future
    //when future return add the returns
    // OK -> back to home
    // ERROR -> dialog
  }

  def backPress() = {
    mainController.changeView("Home")
  }

}