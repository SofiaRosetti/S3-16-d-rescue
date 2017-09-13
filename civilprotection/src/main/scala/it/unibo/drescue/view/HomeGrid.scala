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

    hgap = gap
    vgap = gap
    padding = Insets(insets50)

    val defaultFont = new Font(font20)
    val titleFont = new Font(font30)
    val checkBoxFont = new Font(font18)
    val buttonFont = new Font(font25)

    val lastAlarmsLabel = new Label() {
      text = "LAST ALARMS:"
      font = titleFont
      padding = Insets(insets20)
    }
    val titleBox = new HBox() {
      children = lastAlarmsLabel
      alignment = Pos.Center
    }
    add(titleBox, columnRow0, columnRow0)
    GridPane.setConstraints(titleBox, columnRow0, columnRow0, columnRow2, columnRow1)

    val map = new Label {
      text = "MAP"
      prefWidth = widthHeight300
      prefHeight = widthHeight300
    }
    add(map, columnRow0, columnRow1)
    GridPane.setConstraints(map, columnRow0, columnRow1, columnRow1, columnRow3)

    val filterLabel = new Label() {
      text = "Filter by type:"
      font = defaultFont
      padding = Insets(insets20)
    }
    val filterBox = new HBox() {
      children = filterLabel
      alignment = Pos.Center
    }
    add(filterBox, columnRow1, columnRow1)

    val fireCheckBox = new CheckBox() {
      text = "Fire"
      font = checkBoxFont
      padding = Insets(insets20)
    }
    val earthquakeCheckBox = new CheckBox() {
      text = "Earthquake"
      font = checkBoxFont
      padding = Insets(insets20)
    }
    val landslideCheckBox = new CheckBox() {
      text = "Landslide"
      font = checkBoxFont
      padding = Insets(insets20)
    }
    val avalancheCheckBox = new CheckBox() {
      text = "Avalanche"
      font = checkBoxFont
      padding = Insets(insets20)
    }
    val floodingCheckBox = new CheckBox() {
      text = "Flooding"
      font = checkBoxFont
      padding = Insets(insets20)
    }
    val otherCheckBox = new CheckBox() {
      text = "Other"
      font = checkBoxFont
      padding = Insets(insets20)
    }
    val filterButton = new Button() {
      text = "OK"
      font = Font.font(defaultFont.getFamily, FontWeight.Bold, font20)
      margin = Insets(insets10)
      prefWidth = widthHeight60
    }
    val checkBoxList = new HBox() {
      children.addAll(fireCheckBox, earthquakeCheckBox, landslideCheckBox, avalancheCheckBox, floodingCheckBox, otherCheckBox, filterButton)
      alignment = Pos.Center
    }
    add(checkBoxList, columnRow1, columnRow2)

    val alarmsList = new ListView[String]() {
      items = ObservableBuffer("Alarm 1", "Alarm 2", "Alarm 3", "Alarm 4", "Alarm 5", "Alarm 6", "Alarm 7")
      prefHeight = widthHeight100
      prefWidth = widthHeight400
    }
    val alarmsBox = new HBox() {
      children = alarmsList
      alignment = Pos.Center
    }
    add(alarmsBox, columnRow1, columnRow3)

    val liveRescuesLabel = new Label() {
      text = "Live rescues:"
      font = titleFont
      padding = Insets(insets20)
    }
    val rescuesBox = new HBox() {
      children = liveRescuesLabel
      alignment = Pos.Center
    }
    add(rescuesBox, columnRow0, columnRow4)

    val rescuesList = new ListView[String] {
      items = ObservableBuffer("Rescue 1", "Rescue 2", "Rescue 3", "Rescue 4", "Rescue 5")
      prefHeight = widthHeight100
      prefWidth = widthHeight400
    }
    add(rescuesList, columnRow0, columnRow5)

    val newRescueButton = new Button() {
      text = "New rescue"
      font = buttonFont
      margin = Insets(insets30)
      prefWidth = widthHeight250
      onMouseClicked = (event: MouseEvent) => {
        homeController.newRescuePress()
      }
    }
    val newTeamButton = new Button() {
      text = "New team"
      font = buttonFont
      margin = Insets(insets30)
      prefWidth = widthHeight250
      onMouseClicked = (event: MouseEvent) => {
        homeController.newTeamPress()
      }
    }
    val occupiedTeamsButton = new Button() {
      text = "Occupied teams"
      font = buttonFont
      margin = Insets(insets30)
      prefWidth = widthHeight250
      onMouseClicked = (event: MouseEvent) => {
        homeController.occupiedTeamsPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(insets30)
      children.addAll(newRescueButton, newTeamButton, occupiedTeamsButton)
    }
    add(buttonBox, columnRow1, columnRow5)
  }

  def grid = _grid

}
