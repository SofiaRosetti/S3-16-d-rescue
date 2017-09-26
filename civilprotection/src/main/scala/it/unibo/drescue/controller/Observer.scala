package it.unibo.drescue.controller

/**
  * A trait that observer classes must implement to respect observer pattern
  * in order to be informed of changes in observable objects
  */
trait Observer {

  /**
    * A method every observer class must implement
    * in order to react to a notification
    */
  def onReceivingNotification()

}
