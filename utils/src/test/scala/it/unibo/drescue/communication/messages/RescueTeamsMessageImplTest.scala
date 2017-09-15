package it.unibo.drescue.communication.messages

import it.unibo.drescue.model.RescueTeamImplBuilder
import org.scalatest.FunSuite

object RescueTeamsMessageImplTest {
  private val RT = new RescueTeamImplBuilder()
    .setName("RT1")
    .setLatitude(44.21365)
    .setLongitude(40.85479)
    .setPassword("passwordRT")
    .setPhoneNumber("3657778845")
    .setRescueTeamID("RTID")
    .createRescueTeamImpl()

  private val RTList = RT :: Nil
}

class RescueTeamsMessageImplTest extends FunSuite {

  val RescueTeamsMessage = RescueTeamsMessageImpl(RescueTeamsMessageImplTest.RTList)

  test("Check MessageType") {
    assert(RescueTeamsMessage.getMessageType == MessageType.RESCUE_TEAMS_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    assert(RescueTeamsMessage.rescueTeamsList == RescueTeamsMessageImplTest.RTList)
  }

}
