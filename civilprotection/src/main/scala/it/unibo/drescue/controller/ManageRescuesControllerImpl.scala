package it.unibo.drescue.controller

import it.unibo.drescue.connection.RabbitMQImpl
import it.unibo.drescue.localModel.{EnrolledTeamInfo, Observers}

import scalafx.collections.ObservableBuffer

class ManageRescuesControllerImpl(private var mainController: MainControllerImpl,
                                  var rabbitMQ: RabbitMQImpl) extends Observer {

  mainController.model.addObserver(Observers.ManageRescue, this)

  var obsBuffer = new ObservableBuffer[EnrolledTeamInfo]()

  override def onReceivingNotification(): Unit = {
    obsBuffer.clear()
    mainController.model.enrolledTeamInfoList.forEach(
      (enrolledTeamInfo: EnrolledTeamInfo) => {
        obsBuffer add enrolledTeamInfo
      }
    )
  }

  def sendPressed(wantedRescueTeamID: String) = {

    //TODO get commonCP (see messages in ServerSideHandler and create Server Service)

    //TODO start Ricart Agrawala's Algorithm to occupy the given rescueTeamID

  }

  def stopPressed(wantedRescueTeamID: String) = {

  }

  def backPress() = {
    mainController.changeView("Home")
  }

}
