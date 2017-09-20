package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Trait modelling a request message of associated CPs of a rescue team.
  */
sealed trait GetAssociatedCpMessage {

  /**
    * @return the rescue team's to which find associated CPs
    */
  def rescueTeamID: String

}

case class GetAssociatedCpMessageImpl(override val rescueTeamID: String) extends
  AbstractRoutingMessage(MessageType.ASSOCIATED_CPS_MESSAGE) with GetAssociatedCpMessage with MessageBuilder {

  override def build(): Message = this
}
