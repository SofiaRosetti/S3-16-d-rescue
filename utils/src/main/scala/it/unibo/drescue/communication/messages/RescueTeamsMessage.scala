package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder
import it.unibo.drescue.model.RescueTeam

/**
  * Trait modelling a message containing all rescue teams
  * related to the civil protection who did login.
  */
sealed trait RescueTeamsMessage {

  /**
    *
    * @return the list of rescue teams related to the CP
    */
  def rescueTeamsList: List[RescueTeam]
}

case class RescueTeamsMessageImpl(override val rescueTeamsList: List[RescueTeam])
  extends AbstractMessage(MessageType.RESCUE_TEAMS_MESSAGE) with RescueTeamsMessage with MessageBuilder {

  override def build(): Message = this
}
