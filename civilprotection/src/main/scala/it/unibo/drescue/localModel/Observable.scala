package it.unibo.drescue.localModel

import it.unibo.drescue.controller.Observer

object Observers extends Enumeration {
  val Home, EnrollTeam, ManageRescue = Value
}

trait Observable {

  var homeObserver: Observer = _
  var enrollTeamObserver: Observer = _
  var manageRescueObserver: Observer = _

  def notifyObserver(observerValue: Observers.Value) = observerValue match {
    case Observers.Home => homeObserver.onReceivingNotification()
    case Observers.EnrollTeam => enrollTeamObserver.onReceivingNotification()
    case Observers.ManageRescue => manageRescueObserver.onReceivingNotification()
  }

  def addObserver(observerValue: Observers.Value, observer: Observer) = observerValue match {
    case Observers.Home => homeObserver = observer
    case Observers.EnrollTeam => enrollTeamObserver = observer
    case Observers.ManageRescue => manageRescueObserver = observer
  }


}
