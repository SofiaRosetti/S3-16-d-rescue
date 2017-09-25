package it.unibo.drescue.controller

/**
  * A trait observer classes must implement to respect observer pattern
  */
trait Observer {

  /**
    * A method every observer class must implement
    * in order to react to a notification
    */
  def onReceivingNotification()

}
