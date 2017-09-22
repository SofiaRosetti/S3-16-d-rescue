package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite

object GetAssociatedCpMessageImplTest {
  private val RescueTeamID = "Team01"
}

class GetAssociatedCpMessageImplTest extends FunSuite {

  val GetAssociatedCpMsg = GetAssociatedCpMessageImpl(GetAssociatedCpMessageImplTest.RescueTeamID)

  test("Check MessageType") {
    assert(GetAssociatedCpMsg.getMessageType == MessageType.ASSOCIATED_CPS_MESSAGE.getMessageType)
  }

  test("Check Message RescueTeamID") {
    assert(GetAssociatedCpMsg.rescueTeamID == GetAssociatedCpMessageImplTest.RescueTeamID)
  }

}
