package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.NewTeamControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class NewTeamGrid(private var newTeamController: NewTeamControllerImpl) {

  val _grid = new GridPane() {
    hgap = Gap
    vgap = Gap
    padding = Insets(Insets100)

    val defaultFont = new Font(Font25)
    val titleFont = new Font(Font30)
    val smallFont = new Font(Font20)

    val titleLabel = new Label() {
      text = "New team:"
      font = titleFont
      padding = Insets(Insets10)
    }
    val titleBox = new HBox() {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, ColumnRow0, ColumnRow0)
    GridPane.setConstraints(titleBox, ColumnRow0, ColumnRow0, ColumnRow2, ColumnRow1)

    val selectTeamLabel = new Label() {
      text = "Select existing team:"
      font = defaultFont
      padding = Insets(Insets20)
    }
    val selectBox = new HBox() {
      children = selectTeamLabel
      alignment = Pos.Center
    }
    add(selectBox, ColumnRow0, ColumnRow1)

    val insertTeamLabel = new Label() {
      text = "Insert new team:"
      font = defaultFont
      padding = Insets(Insets20)
    }
    val insertBox = new HBox() {
      children = insertTeamLabel
      alignment = Pos.Center
    }
    add(insertBox, ColumnRow1, ColumnRow1)

    val choices = ObservableBuffer("Team A", "Team B", "Team C")
    val teamChoice = new ComboBox[String] {
      maxWidth = WidthHeight250
      editable = true
      items = choices
    }
    teamChoice.setStyle("-fx-font-size:25")
    add(teamChoice, ColumnRow0, ColumnRow2)

    val nameLabel = new Label() {
      text = "Name:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val nameField = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val nameBox = new HBox() {
      children.addAll(nameLabel, nameField)
      alignment = Pos.CenterRight
    }
    add(nameBox, ColumnRow1, ColumnRow2)

    val districtLabel = new Label() {
      text = "District:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val districtField = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val districtBox = new HBox() {
      children.addAll(districtLabel, districtField)
      alignment = Pos.CenterRight
    }
    add(districtBox, ColumnRow1, ColumnRow3)

    val IDLabel = new Label() {
      text = "ID:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val IDField = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val IDBox = new HBox() {
      children.addAll(IDLabel, IDField)
      alignment = Pos.CenterRight
    }
    add(IDBox, ColumnRow1, ColumnRow4)

    val addButton = new Button {
      text = "Add"
      font = smallFont
      margin = Insets(Insets10)
      prefWidth = WidthHeight100
    }
    val addBox = new HBox {
      children = addButton
      alignment = Pos.CenterRight
    }
    add(addBox, ColumnRow1, ColumnRow5)

    val selectButton = new Button {
      text = "Select"
      font = defaultFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight150
      onMouseClicked = (event: MouseEvent) => {
        newTeamController.selectPress()
      }
    }
    val cancelButton = new Button() {
      text = "Cancel"
      font = defaultFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight150

      onMouseClicked = (event: MouseEvent) => {
        newTeamController.cancelPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(Insets30)
      children.addAll(selectButton, cancelButton)
    }
    add(buttonBox, ColumnRow0, ColumnRow6)
    GridPane.setConstraints(buttonBox, ColumnRow0, ColumnRow6, ColumnRow2, ColumnRow1)
  }

  def grid = _grid

}
