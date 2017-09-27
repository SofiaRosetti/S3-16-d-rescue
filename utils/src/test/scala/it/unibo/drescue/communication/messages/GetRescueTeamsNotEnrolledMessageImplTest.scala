package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite


object GetRescueTeamsNotEnrolledMessageImplTest {
  private val CpID = "FC020"
}

class GetRescueTeamsNotEnrolledMessageImplTest extends FunSuite {

  import GetRescueTeamsNotEnrolledMessageImplTest._

  val getAllRescueTeamsMessage = GetRescueTeamsNotEnrolledMessageImpl(CpID)

  test("Check MessageType") {
    assert(getAllRescueTeamsMessage.getMessageType == MessageType.RESCUE_TEAMS_NOT_ENROLLED_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    assert(getAllRescueTeamsMessage.cpID == GetRescueTeamsNotEnrolledMessageImplTest.CpID)
  }

}
