package it.unibo.drescue.communication.messages

import it.unibo.drescue.communication.builder.MessageBuilder

/**
  * Trait modelling a message for login request from civil protection.
  */
sealed trait CpLoginMessage {

  /**
    *
    * @return the CP's ID who requests login
    */
  def cpID: String

  /**
    *
    * @return the CP's password who requests login
    */
  def password: String
}

case class CpLoginMessageImpl(override val cpID: String, override val password: String) extends AbstractMessage(MessageType.CP_LOGIN_MESSAGE)
  with CpLoginMessage with MessageBuilder {

  override def build(): Message = this

}
