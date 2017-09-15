package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite

object RequestCpAlertsMessageTest {
  private val CpID1 = "RA01"
}

class RequestCpAlertsMessageTest extends FunSuite {

  val alertsMessage = RequestCpAlertsMessageImpl(RequestCpAlertsMessageTest.CpID1)

  test("Check MessageType") {
    assert(alertsMessage.getMessageType == MessageType.REQUEST_CP_ALERTS_MESSAGE.getMessageType)
  }

  test("Check ForwardMessage fields") {
    assert(alertsMessage.cpID == RequestCpAlertsMessageTest.CpID1)
  }

}
