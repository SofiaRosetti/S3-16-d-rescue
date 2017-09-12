package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.HomeControllerImpl

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, CheckBox, Label, ListView}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.{Font, FontWeight}

class HomeGrid(private var homeController: HomeControllerImpl) {

  val _grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(50)

    val defaultFont = new Font(20)
    val titleFont = new Font(30)
    val checkBoxFont = new Font(18)
    val buttonFont = new Font(25)

    val lastAlarmsLabel = new Label() {
      text = "LAST ALARMS:"
      font = titleFont
      padding = Insets(20)
    }
    val titleBox = new HBox() {
      children = lastAlarmsLabel
      alignment = Pos.Center
    }
    add(titleBox, 0, 0)
    GridPane.setConstraints(titleBox, 0, 0, 2, 1)

    val map = new Label {
      text = "MAP"
      prefWidth = 300
      prefHeight = 300
    }
    add(map, 0, 1)
    GridPane.setConstraints(map, 0, 1, 1, 3)

    val filterLabel = new Label() {
      text = "Filter by type:"
      font = defaultFont
      padding = Insets(20)
    }
    val filterBox = new HBox() {
      children = filterLabel
      alignment = Pos.Center
    }
    add(filterBox, 1, 1)

    val fireCheckBox = new CheckBox() {
      text = "Fire"
      font = checkBoxFont
      padding = Insets(20)
    }
    val earthquakeCheckBox = new CheckBox() {
      text = "Earthquake"
      font = checkBoxFont
      padding = Insets(20)
    }
    val landslideCheckBox = new CheckBox() {
      text = "Landslide"
      font = checkBoxFont
      padding = Insets(20)
    }
    val avalancheCheckBox = new CheckBox() {
      text = "Avalanche"
      font = checkBoxFont
      padding = Insets(20)
    }
    val floodingCheckBox = new CheckBox() {
      text = "Flooding"
      font = checkBoxFont
      padding = Insets(20)
    }
    val otherCheckBox = new CheckBox() {
      text = "Other"
      font = checkBoxFont
      padding = Insets(20)
    }
    val filterButton = new Button() {
      text = "OK"
      font = Font.font(defaultFont.getFamily, FontWeight.Bold, 20)
      margin = Insets(10)
      prefWidth = 60
    }
    val checkBoxList = new HBox() {
      children.addAll(fireCheckBox, earthquakeCheckBox, landslideCheckBox, avalancheCheckBox, floodingCheckBox, otherCheckBox, filterButton)
      alignment = Pos.Center
    }
    add(checkBoxList, 1, 2)

    val alarmsList = new ListView[String]() {
      items = ObservableBuffer("Alarm 1", "Alarm 2", "Alarm 3", "Alarm 4", "Alarm 5", "Alarm 6", "Alarm 7")
      prefHeight = 100
      prefWidth = 400
    }
    val alarmsBox = new HBox() {
      children = alarmsList
      alignment = Pos.Center
    }
    add(alarmsBox, 1, 3)

    val liveRescuesLabel = new Label() {
      text = "Live rescues:"
      font = titleFont
      padding = Insets(20)
    }
    val rescuesBox = new HBox() {
      children = liveRescuesLabel
      alignment = Pos.Center
    }
    add(rescuesBox, 0, 4)

    val rescuesList = new ListView[String] {
      items = ObservableBuffer("Rescue 1", "Rescue 2", "Rescue 3", "Rescue 4", "Rescue 5")
      prefHeight = 100
      prefWidth = 400
    }
    add(rescuesList, 0, 5)

    val newRescueButton = new Button() {
      text = "New rescue"
      font = buttonFont
      margin = Insets(30)
      prefWidth = 250
      onMouseClicked = (event: MouseEvent) => {
        homeController.newRescuePress()
      }
    }
    val newTeamButton = new Button() {
      text = "New team"
      font = buttonFont
      margin = Insets(30)
      prefWidth = 250
      onMouseClicked = (event: MouseEvent) => {
        homeController.newTeamPress()
      }
    }
    val occupiedTeamsButton = new Button() {
      text = "Occupied teams"
      font = buttonFont
      margin = Insets(30)
      prefWidth = 250
      onMouseClicked = (event: MouseEvent) => {
        homeController.occupiedTeamsPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(30)
      children.addAll(newRescueButton, newTeamButton, occupiedTeamsButton)
    }
    add(buttonBox, 1, 5)
  }

  def grid = _grid

}