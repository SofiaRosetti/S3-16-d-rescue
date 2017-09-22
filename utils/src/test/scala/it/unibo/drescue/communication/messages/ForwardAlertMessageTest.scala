package it.unibo.drescue.communication.messages

import java.sql.Timestamp

import it.unibo.drescue.model.AlertImplBuilder
import org.scalatest.FunSuite

object ForwardAlertMessageTest {
  private val alert = new AlertImplBuilder()
    .setAlertID(1234)
    .setDistrictID("FC")
    .setEventName("Earthquake")
    .setLatitude(44.45878)
    .setLongitude(33.25789)
    .setTimestamp(new Timestamp(0))
    .setUserID(256)
    .setUpvotes(12)
    .createAlertImpl()
}

class ForwardAlertMessageTest extends FunSuite {

  val ForwardAlertMsg = ForwardAlertMessage(ForwardAlertMessageTest.alert)

  test("Check MessageType") {
    assert(ForwardAlertMsg.getMessageType == MessageType.FORWARD_ALERT_MESSAGE.getMessageType)
  }

  test("Check Message Alert") {
    assert(ForwardAlertMsg.alert == ForwardAlertMessageTest.alert)
  }

}
