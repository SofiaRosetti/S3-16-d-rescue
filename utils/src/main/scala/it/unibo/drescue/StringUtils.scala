package it.unibo.drescue

object StringUtils {

  def isAValidString(string: String): Boolean = {
    string != null && !string.trim.isEmpty
  }

}