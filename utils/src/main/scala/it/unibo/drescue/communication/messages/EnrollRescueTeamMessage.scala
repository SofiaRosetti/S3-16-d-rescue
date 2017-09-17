package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Trait modelling a message for enrollment request to a civil protection.
  */
sealed trait EnrollRescueTeamMessage {

  /**
    *
    * @return the rescueTeam's ID who requests enrollment
    */
  def rescueTeamID: String

  /**
    *
    * @return the CP's ID who accept enrollment
    */
  def cpID: String

}

case class EnrollRescueTeamMessageImpl(override val rescueTeamID: String, override val cpID: String)
  extends AbstractMessage(MessageType.ENROLL_RESCUE_TEAM_MESSAGE)
    with EnrollRescueTeamMessage with MessageBuilder {

  override def build(): Message = this

}
