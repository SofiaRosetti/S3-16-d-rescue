package it.unibo.drescue.controller

import it.unibo.drescue.localModel.AlertEntry

import scalafx.collections.ObservableBuffer

class HomeControllerImpl(private var mainController: MainControllerImpl) extends Observer {

  //TODO start here a request for RequestCpAlertMsg(cpID)
  private var _obsBuffer = new ObservableBuffer[AlertEntry]()

  //TODO
  // - listView with alerts updated by a thread consumer
  // - enrollTeam button -> change view
  // - startRescue button ->
  //      - inactive until alert (from list view) selected
  //      - when pressed -> change view to manage rescue with alert params
  // - manageRescues button -> change view to manage rescues without alert params

  def startAlertsRequest() = {
    // TODO request for alerts with RequestCpAlertMsg(cpID)
  }

  def manageRescuesPress() = {
    mainController.changeView("ManageRescues")
  }

  def newRescuePress() = {
    mainController.changeView("NewRescue")
  }

  def newTeamPress() = {
    mainController.changeView("NewTeam")
  }

  def occupiedTeamsPress() = {
    mainController.changeView("OccupiedTeams")
  }

  def refreshAlertsList() = {
    //obsBuffer.insert(0, "new alert")
  }

  /**
    * TODO
    */
  override def onReceivingNotification(): Unit = {
    obsBuffer = fromAlertEntryListToObsBuffer(mainController.model.lastAlerts)
  }

  def fromAlertEntryListToObsBuffer(alertEntryList: java.util.List[AlertEntry]): ObservableBuffer[AlertEntry] = {
    val obsBuffer = new ObservableBuffer[AlertEntry]()
    alertEntryList.forEach(
      (alertEntry: AlertEntry) => {
        obsBuffer add alertEntry
      }
    )
    obsBuffer
  }

  def obsBuffer = _obsBuffer

  def obsBuffer_=(newBuffer: ObservableBuffer[AlertEntry]) = _obsBuffer = newBuffer
}
