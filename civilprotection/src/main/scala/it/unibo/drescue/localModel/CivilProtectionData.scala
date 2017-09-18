package it.unibo.drescue.localModel

import java.util
import java.util.{ArrayList, List}

import it.unibo.drescue.model.RescueTeam

case class CivilProtectionData() extends Observable {

  private var _cpID: String = null

  private var _lastAlerts: List[AlertEntry] = new ArrayList[AlertEntry]()

  private var _enrolledRescueTeams: List[RescueTeam] = new ArrayList[RescueTeam]()

  private var _notEnrolledRescueTeams: List[RescueTeam] = new ArrayList[RescueTeam]()

  private var _enrolledTeamInfoList: List[EnrolledTeamInfo] = new ArrayList[EnrolledTeamInfo]()

  /**
    * TODO
    *
    * @return
    */
  def cpID = _cpID

  /**
    * TODO
    *
    * @param value
    */
  def cpID_=(value: String): Unit = {
    _cpID = value
  }

  //TODO
  /**
    * TODO
    *
    * @return
    */
  def lastAlerts = _lastAlerts

  /**
    * TODO
    *
    * @param list
    */
  def lastAlerts_=(list: util.List[AlertEntry]): Unit = {
    _lastAlerts = list
    notifyObserver(Observers.Home)
  }

  /**
    * TODO
    *
    * @return
    */
  def enrolledRescueTeams = _enrolledRescueTeams

  /**
    * TODO
    *
    * @param list
    */
  def enrolledRescueTeams_=(list: util.List[RescueTeam]): Unit = {
    _enrolledRescueTeams = list
    notifyObserver(Observers.ManageRescue)
  }

  /**
    * TODO
    *
    * @return
    */
  def notEnrolledRescueTeams = _notEnrolledRescueTeams

  /**
    * TODO
    *
    * @param list
    */
  def notEnrolledRescueTeams_=(list: util.List[RescueTeam]): Unit = {
    _notEnrolledRescueTeams = list
    notifyObserver(Observers.EnrollTeam)
  }

  /**
    * TODO
    *
    * @return
    */
  def enrolledTeamInfoList = _enrolledTeamInfoList

  /**
    * TODO
    *
    * @param list
    */
  def enrolledTeamInfoList_=(list: util.List[EnrolledTeamInfo]): Unit = {
    _enrolledTeamInfoList = list
    notifyObserver(Observers.ManageRescue)
  }

}
