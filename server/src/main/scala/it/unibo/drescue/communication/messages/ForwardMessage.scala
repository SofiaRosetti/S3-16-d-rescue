package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder
import it.unibo.drescue.model.ObjectModel

/**
  * Trait modelling a message to forward.
  */
sealed trait ForwardMessage {

  /**
    * @return the cpID to which forward the message
    */
  def cpID: String

  /**
    * @return the objectModel to forward
    */
  def objectModel: ObjectModel

}

/**
  * Class that
  *
  * @param cpID
  * @param objectModel
  */
case class ForwardObjectMessage(override val cpID: String, override val objectModel: ObjectModel)
  extends AbstractMessage(MessageType.FORWARD_MESSAGE) with ForwardMessage with MessageBuilder {

  override def build(): Message = this

}

