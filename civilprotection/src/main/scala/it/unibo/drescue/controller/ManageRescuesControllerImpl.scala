package it.unibo.drescue.controller

import it.unibo.drescue.localModel.Observers

class ManageRescuesControllerImpl(private var mainController: MainControllerImpl) extends Observer {

  mainController.model.addObserver(Observers.ManageRescue, this)

  /**
    * TODO
    */
  override def onReceivingNotification(): Unit = {
    //TODO
  }
}
