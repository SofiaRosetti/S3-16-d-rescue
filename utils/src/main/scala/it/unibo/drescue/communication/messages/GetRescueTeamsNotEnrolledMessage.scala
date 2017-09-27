package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Trait modelling a message for rescue teams not enrolled to a civil protection.
  */
sealed trait GetRescueTeamsNotEnrolledMessage {

  /**
    *
    * @return the CP's ID who requests the list of rescue teams not enrolled
    */
  def cpID: String
}

/**
  * Class modelling a message for performing a request for
  * all rescue teams not enrolled to a specified civil protection.
  */
case class GetRescueTeamsNotEnrolledMessageImpl(override val cpID: String)
  extends AbstractMessage(MessageType.RESCUE_TEAMS_NOT_ENROLLED_MESSAGE)
    with GetRescueTeamsNotEnrolledMessage with MessageBuilder {

  override def build(): Message = this
}