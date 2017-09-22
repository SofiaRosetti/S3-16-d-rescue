package it.unibo.drescue.communication.messages

import java.util

import it.unibo.drescue.model.CivilProtectionImpl
import org.scalatest.FunSuite

object CPsMessageImplTest {
  private val Cp = new CivilProtectionImpl("CP01", "password")
  private val CpList = new util.ArrayList[CivilProtectionImpl]()
}

class CPsMessageImplTest extends FunSuite {

  CPsMessageImplTest.CpList.add(CPsMessageImplTest.Cp)
  val CPsMessage = CPsMessageImpl(CPsMessageImplTest.CpList)

  test("Check MessageType") {
    assert(CPsMessage.getMessageType == MessageType.CPS_MESSAGE.getMessageType)
  }

  test("Check Message CpList") {
    assert(CPsMessage.cpList == CPsMessageImplTest.CpList)
  }

}
