package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite

object EnrollRescueTeamMessageImplTest {
  private val CpID = "FC020"
  private val RescueTeamID = "RT01"
}

class EnrollRescueTeamMessageImplTest extends FunSuite {

  import EnrollRescueTeamMessageImplTest.{CpID, RescueTeamID}

  val enrollTeamMessage = EnrollRescueTeamMessageImpl(RescueTeamID, CpID)

  test("Check MessageType") {
    assert(enrollTeamMessage.getMessageType == MessageType.ENROLL_RESCUE_TEAM_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    import EnrollRescueTeamMessageImplTest._
    assert(enrollTeamMessage.cpID == CpID
      && enrollTeamMessage.rescueTeamID == RescueTeamID)
  }

}
