package it.unibo.drescue.localModel

import scalafx.beans.property.StringProperty

/**
  * A class representing an entry of the manage rescues view rescue teams list
  *
  * @param teamID_       the team ID
  * @param teamName_     the team name
  * @param phoneNumber_  the team phone number
  * @param availability_ the team availability
  * @param cpID_         the civil protection ID
  * @param alertID_      the alert ID
  */
class EnrolledTeamInfo(teamID_ : String,
                       teamName_ : String,
                       phoneNumber_ : String,
                       availability_ : Boolean,
                       cpID_ : String,
                       alertID_ : String) {

  val teamID = new StringProperty(this, "teamID", teamID_)
  val teamName = new StringProperty(this, "teamName", teamName_)
  val phoneNumber = new StringProperty(this, "phoneNumber", phoneNumber_)
  val availability = new StringProperty(this, "availability", availability_.toString)
  val cpID = new StringProperty(this, "cpID", cpID_)
  val alertID = new StringProperty(this, "alertID", alertID_)

}
