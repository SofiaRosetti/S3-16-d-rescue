package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite

class GetRescueTeamsNotEnrolledMessageImplTest extends FunSuite {

  val getAllRescueTeamsMessage = GetRescueTeamsNotEnrolledMessage()

  test("Check MessageType") {
    assert(getAllRescueTeamsMessage.getMessageType == MessageType.RESCUE_TEAMS_NOT_ENROLLED_MESSAGE.getMessageType)
  }

}
