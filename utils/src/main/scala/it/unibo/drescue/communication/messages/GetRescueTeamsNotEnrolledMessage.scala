package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Class modelling a message for performing a request for all rescue teams
  * from a civil protection.
  */
case class GetRescueTeamsNotEnrolledMessage()
  extends AbstractMessage(MessageType.RESCUE_TEAMS_NOT_ENROLLED_MESSAGE) with MessageBuilder {

  override def build(): Message = this
}
