package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.HomeControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, CheckBox, Label, ListView}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.{Font, FontWeight}

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
      text = "LAST ALARMS:"
      font = titleFont
      padding = Insets(Insets20)
    }
    val titleBox = new HBox() {
      children = lastAlarmsLabel
      alignment = Pos.Center
    }
    add(titleBox, ColumnRow0, ColumnRow0)
    GridPane.setConstraints(titleBox, ColumnRow0, ColumnRow0, ColumnRow2, ColumnRow1)

    val map = new Label {
      text = "MAP"
      prefWidth = WidthHeight300
      prefHeight = WidthHeight300
    }
    add(map, ColumnRow0, ColumnRow1)
    GridPane.setConstraints(map, ColumnRow0, ColumnRow1, ColumnRow1, ColumnRow3)

    val filterLabel = new Label() {
      text = "Filter by type:"
      font = defaultFont
      padding = Insets(Insets20)
    }
    val filterBox = new HBox() {
      children = filterLabel
      alignment = Pos.Center
    }
    add(filterBox, ColumnRow1, ColumnRow1)

    val fireCheckBox = new CheckBox() {
      text = "Fire"
      font = checkBoxFont
      padding = Insets(Insets20)
    }
    val earthquakeCheckBox = new CheckBox() {
      text = "Earthquake"
      font = checkBoxFont
      padding = Insets(Insets20)
    }
    val landslideCheckBox = new CheckBox() {
      text = "Landslide"
      font = checkBoxFont
      padding = Insets(Insets20)
    }
    val avalancheCheckBox = new CheckBox() {
      text = "Avalanche"
      font = checkBoxFont
      padding = Insets(Insets20)
    }
    val floodingCheckBox = new CheckBox() {
      text = "Flooding"
      font = checkBoxFont
      padding = Insets(Insets20)
    }
    val otherCheckBox = new CheckBox() {
      text = "Other"
      font = checkBoxFont
      padding = Insets(Insets20)
    }
    val filterButton = new Button() {
      text = "OK"
      font = Font.font(defaultFont.getFamily, FontWeight.Bold, Font20)
      margin = Insets(Insets10)
      prefWidth = WidthHeight60
    }
    val checkBoxList = new HBox() {
      children.addAll(fireCheckBox, earthquakeCheckBox, landslideCheckBox, avalancheCheckBox, floodingCheckBox, otherCheckBox, filterButton)
      alignment = Pos.Center
    }
    add(checkBoxList, ColumnRow1, ColumnRow2)

    val alarmsList = new ListView[String]() {
      var buffer = homeController._obsBuffer
      buffer += "first alert"
      items = buffer
      prefHeight = WidthHeight100
      prefWidth = WidthHeight400
    }
    val alarmsBox = new HBox() {
      children = alarmsList
      alignment = Pos.Center
    }
    add(alarmsBox, ColumnRow1, ColumnRow3)

    val liveRescuesLabel = new Label() {
      text = "Live rescues:"
      font = titleFont
      padding = Insets(Insets20)
    }
    val rescuesBox = new HBox() {
      children = liveRescuesLabel
      alignment = Pos.Center
    }
    add(rescuesBox, ColumnRow0, ColumnRow4)

    val rescuesList = new ListView[String] {
      items = ObservableBuffer("Rescue 1", "Rescue 2", "Rescue 3", "Rescue 4", "Rescue 5")
      prefHeight = WidthHeight100
      prefWidth = WidthHeight400
    }
    add(rescuesList, ColumnRow0, ColumnRow5)

    val newRescueButton = new Button() {
      text = "New rescue"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight250
      onMouseClicked = (event: MouseEvent) => {
        homeController.newRescuePress()
        //homeController.refreshAlertsList()
      }
    }
    val newTeamButton = new Button() {
      text = "New team"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight250
      onMouseClicked = (event: MouseEvent) => {
        homeController.newTeamPress()
      }
    }
    val occupiedTeamsButton = new Button() {
      text = "Occupied teams"
      font = buttonFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight250
      onMouseClicked = (event: MouseEvent) => {
        homeController.occupiedTeamsPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(Insets30)
      children.addAll(newRescueButton, newTeamButton, occupiedTeamsButton)
    }
    add(buttonBox, ColumnRow1, ColumnRow5)
  }

  def grid = _grid

}
