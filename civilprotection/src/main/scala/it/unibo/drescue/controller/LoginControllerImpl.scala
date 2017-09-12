package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel

class LoginControllerImpl(private var model: List[ObjectModel],
                          private var mainController: MainControllerImpl) {

  def loginPress(username: String, password: String) = {
    println(username)
    println(password)
    mainController.changeView("Home")
  }

}
