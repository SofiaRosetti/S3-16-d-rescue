package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder
import it.unibo.drescue.model.UpvotedAlertImpl

case class ForwardUpvotedAlertMessage(val upvotedAlert: UpvotedAlertImpl) extends AbstractMessage(MessageType.FORWARD_UPVOTED_ALERT_MESSAGE) with MessageBuilder {

  override def build(): Message = this

}
