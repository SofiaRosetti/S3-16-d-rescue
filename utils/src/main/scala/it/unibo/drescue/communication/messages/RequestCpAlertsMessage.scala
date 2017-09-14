package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Trait modelling a message for performing an alerts request
  * from civil protection.
  */
sealed trait RequestCpAlertsMessage {

  /**
    *
    * @return the cpID who request alerts
    */
  def cpID: String

}

/**
  * Class modelling a message for performing an alerts request
  * from civil protection.
  *
  * @param cpID the cpID who request alerts
  */
case class RequestCpAlertsMessageImpl(override val cpID: String)
  extends AbstractMessage(MessageType.REQUEST_CP_ALERTS_MESSAGE) with RequestCpAlertsMessage with MessageBuilder {

  override def build(): Message = this
}
