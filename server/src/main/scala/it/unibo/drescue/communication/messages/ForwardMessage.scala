package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder
import it.unibo.drescue.model.ObjectModel

import scala.collection.mutable.ListBuffer

/**
  * Trait modelling a message to forward.
  */
sealed trait ForwardMessage {

  /**
    * @return the cpID list name to which forward the message
    */
  def cpIDList: ListBuffer[String]

  /**
    * @return the objectModel to forward
    */
  def objectModel: ObjectModel

}

/**
  * Class modelling a message to forward.
  *
  * @param cpIDList    cpID list name to which forward the message
  * @param objectModel objectModel to forward
  */
case class ForwardObjectMessage(override val cpIDList: ListBuffer[String], override val objectModel: ObjectModel)
  extends AbstractMessage(MessageType.FORWARD_MESSAGE) with ForwardMessage with MessageBuilder {

  override def build(): Message = this

}

