package it.unibo.drescue

import org.scalatest.FunSuite

class StringUtilsTest extends FunSuite {

  test("String must be not null or empty") {
    val rightString = "prova"
    val nullString = null
    val emptyString = ""

    assert(StringUtils.isAValidString(rightString))
    assert(!StringUtils.isAValidString(nullString))
    assert(!StringUtils.isAValidString(emptyString))
  }

}