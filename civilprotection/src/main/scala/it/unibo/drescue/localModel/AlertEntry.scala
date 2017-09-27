package it.unibo.drescue.localModel

import java.sql.Timestamp

import scalafx.beans.property.StringProperty

/**
  * A class representing an entry of the home view alerts list
  *
  * @param _alertID    the alert ID
  * @param _timestamp  the alert timestamp
  * @param _latitude   the alert latitude
  * @param _longitude  the alert longitude
  * @param _userID     the user ID
  * @param _eventName  the event name
  * @param _districtID the district ID
  * @param _upvotes    the alert upvotes
  */
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
