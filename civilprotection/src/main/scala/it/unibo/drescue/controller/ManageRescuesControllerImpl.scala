package it.unibo.drescue.controller

import com.rabbitmq.client.BuiltinExchangeType
import it.unibo.drescue.communication.CPConsumer
import it.unibo.drescue.connection.RabbitMQImpl
import it.unibo.drescue.localModel.Observers
import it.unibo.drescue.utils.CoordinatorImpl

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
