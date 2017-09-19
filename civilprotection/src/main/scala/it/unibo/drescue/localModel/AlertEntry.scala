package it.unibo.drescue.localModel

import java.sql.Timestamp

import scalafx.beans.property.StringProperty

class AlertEntry(val _alertID: Int,
                 val _timestamp: Timestamp,
                 val _latitude: Double,
                 val _longitude: Double,
                 val _userID: Int,
                 val _eventName: String,
                 val _districtID: String,
                 val _upvotes: Int) {

  val alertID = new StringProperty(this, "alertID", _alertID.toString)
  val timestamp = new StringProperty(this, "timestamp", _timestamp.toString)
  val latitude = new StringProperty(this, "latitude", _latitude.toString)
  val longitude = new StringProperty(this, "longitude", _longitude.toString)
  val userID = new StringProperty(this, "userID", _userID.toString)
  val eventName = new StringProperty(this, "eventName", _eventName)
  val districtID = new StringProperty(this, "districtID", _districtID)
  val upvotes = new StringProperty(this, "upvotes", _upvotes.toString)

}
