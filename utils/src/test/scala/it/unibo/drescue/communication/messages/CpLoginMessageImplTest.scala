package it.unibo.drescue.communication.messages

import org.scalatest.FunSuite

object CpLoginMessageImplTest {
  private val CpID = "FC020"
  private val CpPassword = "password020"
}

class CpLoginMessageImplTest extends FunSuite {

  val CpLoginMessage = CpLoginMessageImpl(CpLoginMessageImplTest.CpID, CpLoginMessageImplTest.CpPassword)

  test("Check MessageType") {
    assert(CpLoginMessage.getMessageType == MessageType.CP_LOGIN_MESSAGE.getMessageType)
  }

  test("Check Message Fields") {
    assert(CpLoginMessage.cpID == CpLoginMessageImplTest.CpID
      && CpLoginMessage.password == CpLoginMessageImplTest.CpPassword)
  }

}
