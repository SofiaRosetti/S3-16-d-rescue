package it.unibo.drescue.view

import scalafx.beans.property.StringProperty

class TableEntry(teamName_ : String, phoneNumber_ : String, availability_ : Boolean, cpID_ : String, alertID_ : Int) {

  val teamName = new StringProperty(this, "teamName", teamName_)
  val phoneNumber = new StringProperty(this, "phoneNumber", phoneNumber_)
  val availability = new StringProperty(this, "availability", availability_.toString)
  val cpID = new StringProperty(this, "cpID", cpID_)
  val alertID = new StringProperty(this, "alertID", alertID_.toString)

}
