package it.unibo.drescue.localModel

import java.sql.Timestamp

import scalafx.beans.property.StringProperty

class AlertEntry(alertID_ : Int,
                 timestamp_ : Timestamp,
                 latitude_ : Double,
                 longitude_ : Double,
                 userID_ : Int,
                 eventName_ : String,
                 districtID_ : String,
                 upvotes_ : Int) {

  val alertID = new StringProperty(this, "alertID", alertID_.toString)
  val timestamp = new StringProperty(this, "timestamp", timestamp_.toString)
  val latitude = new StringProperty(this, "latitude", latitude_.toString)
  val longitude = new StringProperty(this, "longitude", longitude_.toString)
  val userID = new StringProperty(this, "userID", userID_.toString)
  val eventName = new StringProperty(this, "eventName", eventName)
  val districtID = new StringProperty(this, "districtID", districtID)
  val upvotes = new StringProperty(this, "upvotes", upvotes_.toString)

}
