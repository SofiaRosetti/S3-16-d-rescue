package it.unibo.drescue

import org.scalatest.FunSuite

class StringUtilsTest extends FunSuite {

  test("String must not be null or empty") {
    val rightString = "test"
    val nullString = null
    val emptyString = ""

    assert(StringUtils.isAValidString(rightString))
    assert(!StringUtils.isAValidString(nullString))
    assert(!StringUtils.isAValidString(emptyString))
  }

}