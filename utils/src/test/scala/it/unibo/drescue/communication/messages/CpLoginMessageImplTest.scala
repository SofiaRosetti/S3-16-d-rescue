package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite

object CpLoginMessageImplTest {
  private val CpID = "FC020"
  private val CpPassword = "password020"
}

class CpLoginMessageImplTest extends FunSuite {

  val cpLoginMessage = CpLoginMessageImpl(CpLoginMessageImplTest.CpID, CpLoginMessageImplTest.CpPassword)

  test("Check MessageType") {
    assert(cpLoginMessage.getMessageType == MessageType.CP_LOGIN_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    assert(cpLoginMessage.cpID == CpLoginMessageImplTest.CpID
      && cpLoginMessage.password == CpLoginMessageImplTest.CpPassword)
  }

}
