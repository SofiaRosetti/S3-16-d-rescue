package it.unibo.drescue.controller

import it.unibo.drescue.model.ObjectModel
import it.unibo.drescue.view.MainView

class MainControllerImpl(private var model: List[ObjectModel]) {

  var view = new MainView(null, null, null, null, null, null)

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def changeView(nextView: String) = {
    view.changeView(nextView)
  }
}
