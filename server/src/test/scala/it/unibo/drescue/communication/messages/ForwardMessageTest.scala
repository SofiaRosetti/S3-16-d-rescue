package it.unibo.drescue.communication.messages

import it.unibo.drescue.model.{AlertImpl, AlertImplBuilder}
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

object ForwardMessageTest {
  private val CpID1 = "RA01"
  private val CpID2 = "RA02"
}

class ForwardMessageTest extends FunSuite {

  val alertImpl: AlertImpl = new AlertImplBuilder().createAlertImpl()
  val cpIDList: ListBuffer[String] = ListBuffer(ForwardMessageTest.CpID1, ForwardMessageTest.CpID2)
  val forwardMessage = ForwardObjectMessage(cpIDList, alertImpl)

  test("Check MessageType") {
    assert(forwardMessage.getMessageType == MessageType.FORWARD_MESSAGE.getMessageType)
  }

  test("Check ForwardMessage fields") {
    assert(cpIDList == forwardMessage.cpIDList)
    assert(forwardMessage.objectModel.asInstanceOf[AlertImpl].getClass == classOf[AlertImpl])
  }


}
