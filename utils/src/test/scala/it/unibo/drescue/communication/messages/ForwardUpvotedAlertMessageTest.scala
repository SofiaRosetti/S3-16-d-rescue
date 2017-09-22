package it.unibo.drescue.communication.messages

import it.unibo.drescue.model.UpvotedAlertImpl
import org.scalatest.FunSuite

object ForwardUpvotedAlertMessageTest {
  private val UpvotedAlert = new UpvotedAlertImpl(256, 12477)
}

class ForwardUpvotedAlertMessageTest extends FunSuite {

  val ForwardUpvotedAlertMsg = ForwardUpvotedAlertMessage(ForwardUpvotedAlertMessageTest.UpvotedAlert)

  test("Check MessageType") {
    assert(ForwardUpvotedAlertMsg.getMessageType == MessageType.FORWARD_UPVOTED_ALERT_MESSAGE.getMessageType)
  }

  test("Check Message UpvotedAlert") {
    assert(ForwardUpvotedAlertMsg.upvotedAlert == ForwardUpvotedAlertMessageTest.UpvotedAlert)
  }
}
