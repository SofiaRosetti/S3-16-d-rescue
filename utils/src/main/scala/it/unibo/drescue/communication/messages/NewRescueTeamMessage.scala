package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Class modelling a message for performing a request
  * to add a rescue teams into DB.
  */
case class NewRescueTeamMessage(
                                 rescueTeamID: String,
                                 rescueTeamName: String,
                                 rescueTeamLatitude: Double,
                                 rescueTeamLongitude: Double,
                                 rescueTeamPhoneNumber: String,
                                 rescueTeamPassword: String = "passwordToChange"
                               )
  extends AbstractMessage(MessageType.ADD_RESCUE_TEAM_MESSAGE)
    with MessageBuilder {

  override def build(): Message = this

}