package it.unibo.drescue.controller

import it.unibo.drescue.connection.RabbitMQImpl
import it.unibo.drescue.localModel.Observers

class ManageRescuesControllerImpl(private var mainController: MainControllerImpl,
                                  var rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.ManageRescue, this)

  /**
    * TODO
    */
  override def onReceivingNotification(): Unit = {
    //TODO
  }

  def sendPressed(wantedRescueTeamID: String) = {


  }

  def stopPressed(wantedRescueTeamID: String) = {}

  def backPress() = {
    mainController.changeView("Home")
  }

}
