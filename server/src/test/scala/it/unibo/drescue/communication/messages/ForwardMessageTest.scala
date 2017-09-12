package it.unibo.drescue.communication.messages

import it.unibo.drescue.model.{AlertImpl, AlertImplBuilder}
import org.scalatest.FunSuite

object ForwardMessageTest {
  val cpID = "RA01"
}

class ForwardMessageTest extends FunSuite {

  val alertImpl: AlertImpl = new AlertImplBuilder().createAlertImpl()
  val forwardMessage = ForwardObjectMessage(ForwardMessageTest.cpID, alertImpl)

  test("Check MessageType") {
    assert(forwardMessage.getMessageType === MessageType.FORWARD_MESSAGE.getMessageType)
  }

  test("Check ForwardMessage fields") {
    assert(ForwardMessageTest.cpID === forwardMessage.cpID)
    assert(forwardMessage.objectModel.asInstanceOf[AlertImpl].getClass === classOf[AlertImpl])
  }


}
