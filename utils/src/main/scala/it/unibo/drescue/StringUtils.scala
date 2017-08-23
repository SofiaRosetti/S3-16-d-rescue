package it.unibo.drescue

/**
  * Utility class for strings
  */
object StringUtils {

  /**
    * Checks if the string passed is null or empty
    *
    * @param string
    * @return true if string is null or empty, false otherwise
    */
  def isAValidString(string: String): Boolean = {
    string != null && !string.trim.isEmpty
  }

}