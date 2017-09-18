package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.HomeControllerImpl
import it.unibo.drescue.localModel.AlertEntry
import it.unibo.drescue.view.ViewConstants._

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.TableColumn._
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class HomeGrid(private var homeController: HomeControllerImpl) {

  val _grid = new GridPane() {

    hgap = Gap
    vgap = Gap
    padding = Insets(Insets50)

    val defaultFont = new Font(Font20)
    val titleFont = new Font(Font30)
    val checkBoxFont = new Font(Font18)
    val buttonFont = new Font(Font25)

    val lastAlarmsLabel = new Label() {
      text = "LAST ALERTS:"
      font = titleFont
      padding = Insets(Insets20)
    }
    val titleBox = new HBox() {
      children = lastAlarmsLabel
      alignment = Pos.Center
    }
    add(titleBox, ColumnRow0, ColumnRow0)
    GridPane.setConstraints(titleBox, ColumnRow0, ColumnRow0, ColumnRow2, ColumnRow1)

    var alertsEntries = homeController.obsBuffer

    val AlertTable = new TableView[AlertEntry](alertsEntries) {
      maxHeight = WidthHeight200
      columns ++= List(
        new TableColumn[AlertEntry, String]() {
          text = "Alert ID"
          cellValueFactory = {
            _.value.alertID
          }
          prefWidth = WidthHeight120
        }
        ,
        new TableColumn[AlertEntry, String]() {
          text = "Timestamp"
          cellValueFactory = {
            _.value.timestamp
          }
          prefWidth = WidthHeight230
        },
        new TableColumn[AlertEntry, String]() {
          text = "Latitude"
          cellValueFactory = {
            _.value.latitude
          }
          prefWidth = WidthHeight100
        },
        new TableColumn[AlertEntry, String]() {
          text = "Longitude"
          cellValueFactory = {
            _.value.longitude
          }
          prefWidth = WidthHeight100
        },
        new TableColumn[AlertEntry, String]() {
          text = "User ID"
          cellValueFactory = {
            _.value.userID
          }
          prefWidth = WidthHeight100
        },
        new TableColumn[AlertEntry, String]() {
          text = "Event name"
          cellValueFactory = {
            _.value.eventName
          }
          prefWidth = WidthHeight150
        },
        new TableColumn[AlertEntry, String]() {
          text = "District ID"
          cellValueFactory = {
            _.value.districtID
          }
          prefWidth = WidthHeight100
        },
        new TableColumn[AlertEntry, String]() {
          text = "Upvotes"
          cellValueFactory = {
            _.value.upvotes
          }
          prefWidth = WidthHeight100
        }
      )
    }
    add(AlertTable, ColumnRow0, ColumnRow1)


    val EnrollTeamButton = new Button() {
      text = "Enroll team"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight250
      onMouseClicked = (event: MouseEvent) => { // TODO change event after pressure
        homeController.newTeamPress()
      }
    }
    val ManageRescuesButton = new Button() {
      text = "Manage rescues"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight250
      onMouseClicked = (event: MouseEvent) => {
        //homeController.occupiedTeamsPress()
        homeController.manageRescuesPress()
      }
    }
    val StartRescueButton = new Button() {
      text = "Start rescue"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight250
      onMouseClicked = (event: MouseEvent) => { // TODO change event after pressure
        homeController.newRescuePress()
        //homeController.refreshAlertsList()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(Insets10)
      children.addAll(EnrollTeamButton, ManageRescuesButton, StartRescueButton)
    }
    add(buttonBox, ColumnRow0, ColumnRow2)
  }

  def grid = _grid

}
