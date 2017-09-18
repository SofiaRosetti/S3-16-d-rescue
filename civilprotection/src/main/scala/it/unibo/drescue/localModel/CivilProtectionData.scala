package it.unibo.drescue.localModel

import java.util.{ArrayList, List}

import it.unibo.drescue.model.RescueTeam

case class CivilProtectionData() {

  var cpID: String = null

  var lastAlerts: List[AlertEntry] = new ArrayList[AlertEntry]()

  var enrolledRescueTeams: List[RescueTeam] = new ArrayList[RescueTeam]()

  var notEnrolledRescueTeams: List[RescueTeam] = new ArrayList[RescueTeam]()

  var enrolledTeamInfoList: List[EnrolledTeamInfo] = new ArrayList[EnrolledTeamInfo]()

}
