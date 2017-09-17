package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.ManageRescuesControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{Button, Label, TableColumn, TableView}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class ManageRescuesGrid(private var manageRescuesController: ManageRescuesControllerImpl) {

  val _grid = new GridPane() {

    hgap = Gap
    vgap = 5
    padding = Insets(Insets50)

    val defaultFont = new Font(Font20)
    val titleFont = new Font(Font30)
    val checkBoxFont = new Font(Font18)
    val buttonFont = new Font(Font25)

    val ManageRescuesLabel = new Label() {
      text = "MANAGE RESCUES:"
      font = titleFont
      padding = Insets(Insets20)
    }
    val titleBox = new HBox() {
      children = ManageRescuesLabel
      alignment = Pos.Center
    }
    add(titleBox, ColumnRow0, ColumnRow0)
    GridPane.setConstraints(titleBox, ColumnRow0, ColumnRow0, ColumnRow2, ColumnRow1)

    val AlertLabel = new Label() {
      text = "Alert: 2017-09-20 16:51:19 Earthquake lat: 44.139123 long: 12.243698 FC user: 27549 upvotes: 36"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val alertBox = new HBox() {
      children = AlertLabel
      alignment = Pos.Center
    }
    add(alertBox, ColumnRow0, ColumnRow1)

    val ChooseTeamLabel = new Label() {
      text = "Choose team:"
      font = buttonFont
      padding = Insets(Insets10)
    }
    val chooseBox = new HBox() {
      children = ChooseTeamLabel
      alignment = Pos.Center
    }
    add(chooseBox, ColumnRow0, ColumnRow3)

    var entries = ObservableBuffer[TableEntry](
      new TableEntry("Rescue team 001", "3335874588", true, "FC001", 256)
    )

    val Table = new TableView[TableEntry](entries) {
      maxHeight = 200
      columns ++= List(
        new TableColumn[TableEntry, String]() {
          text = "Team name"
          cellValueFactory = {
            _.value.teamName
          }
          prefWidth = 300
        },
        new TableColumn[TableEntry, String]() {
          text = "Phone number"
          cellValueFactory = {
            _.value.phoneNumber
          }
          prefWidth = 200
        },
        new TableColumn[TableEntry, String]() {
          text = "Availability"
          cellValueFactory = {
            _.value.availability
          }
          prefWidth = 150
        },
        new TableColumn[TableEntry, String]() {
          text = "Cp ID"
          cellValueFactory = {
            _.value.cpID
          }
          prefWidth = 150
        },
        new TableColumn[TableEntry, String]() {
          text = "Alert ID"
          cellValueFactory = {
            _.value.alertID
          }
          prefWidth = 150
        }
      )
    }
    add(Table, ColumnRow0, ColumnRow4)

    val SendButton = new Button() {
      text = "Send"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight200
      onMouseClicked = (event: MouseEvent) => {
        //homeController.newTeamPress()
      }
    }
    val StopButton = new Button() {
      text = "Stop"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight200
      onMouseClicked = (event: MouseEvent) => {
        //homeController.occupiedTeamsPress()
      }
    }
    val BackButton = new Button() {
      text = "Back"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight200
      onMouseClicked = (event: MouseEvent) => {
        //homeController.newRescuePress()
        //homeController.refreshAlertsList()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(Insets10)
      children.addAll(SendButton, StopButton, BackButton)
    }
    add(buttonBox, ColumnRow0, ColumnRow5)
  }

  def grid = _grid
}
