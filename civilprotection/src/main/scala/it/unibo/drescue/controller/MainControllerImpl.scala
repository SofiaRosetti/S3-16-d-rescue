package it.unibo.drescue.controller

import it.unibo.drescue.localModel.CivilProtectionData
import it.unibo.drescue.view.MainView

class MainControllerImpl(var model: CivilProtectionData) {

  var view = new MainView(null, null, null, null, null, null, null)

  def addView(viewValue: MainView): Unit = {
    view = viewValue
  }

  def changeView(nextView: String) = {
    view.changeView(nextView)
  }

  def _view: MainView = view

}
