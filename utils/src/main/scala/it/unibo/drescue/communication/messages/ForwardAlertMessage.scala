package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder
import it.unibo.drescue.model.AlertImpl

case class ForwardAlertMessage(val alert: AlertImpl) extends AbstractMessage(MessageType.FORWARD_ALERT_MESSAGE) with MessageBuilder {

  override def build(): Message = this

}
