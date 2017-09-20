package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder
import it.unibo.drescue.model.CivilProtectionImpl

/**
  * Trait modelling a message containing all CPs
  * related to the rescue team selected to start or
  * stop rescue.
  */
sealed trait CPsMessage {

  /**
    *
    * @return the list of CPs related to the rescue team
    */
  def cpList: java.util.List[CivilProtectionImpl]
}

case class CPsMessageImpl(override val cpList: java.util.List[CivilProtectionImpl])
  extends AbstractMessage(MessageType.CPS_MESSAGE) with CPsMessage with MessageBuilder {

  override def build(): Message = this
}
