package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite


object NewRescueTeamMessageImplTest {
  private val RTId = "RT14D"
  private val RTName = "Croce blu della Romagna"
  private val RTLatitude = 44.21365
  private val RTLongitude = 40.85479
  private val RTPhoneNumber = "3657778845"
}

class NewRescueTeamMessageImplTest extends FunSuite {

  import NewRescueTeamMessageImplTest._

  val newRescueTeamMessage: NewRescueTeamMessage = new NewRescueTeamMessage(
    rescueTeamID = RTId,
    rescueTeamName = RTName,
    rescueTeamLatitude = RTLatitude,
    rescueTeamLongitude = RTLongitude,
    rescueTeamPhoneNumber = RTPhoneNumber
  )

  test("Check MessageType") {
    assert(newRescueTeamMessage.getMessageType == MessageType.ADD_RESCUE_TEAM_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    assert(newRescueTeamMessage.rescueTeamID == NewRescueTeamMessageImplTest.RTId)
    assert(newRescueTeamMessage.rescueTeamName == NewRescueTeamMessageImplTest.RTName)
    assert(newRescueTeamMessage.rescueTeamLatitude == NewRescueTeamMessageImplTest.RTLatitude)
    assert(newRescueTeamMessage.rescueTeamLongitude == NewRescueTeamMessageImplTest.RTLongitude)
    assert(newRescueTeamMessage.rescueTeamPhoneNumber == NewRescueTeamMessageImplTest.RTPhoneNumber)
  }

}
