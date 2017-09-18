package it.unibo.drescue.communication.messages

import java.util

import it.unibo.drescue.model.{RescueTeamImpl, RescueTeamImplBuilder}
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
}

class RescueTeamsMessageImplTest extends FunSuite {

  var RTList: java.util.List[RescueTeamImpl] = new util.ArrayList[RescueTeamImpl]() //TODO refactor
  RTList add RescueTeamsMessageImplTest.RT

  var RescueTeamsMessage = RescueTeamsMessageImpl(RTList)

  test("Check MessageType") {
    assert(RescueTeamsMessage.getMessageType == MessageType.RESCUE_TEAMS_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    assert(RescueTeamsMessage.rescueTeamsList == RTList)
  }

}
