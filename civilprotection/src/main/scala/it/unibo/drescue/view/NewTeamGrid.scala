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
    hgap = gap
    vgap = gap
    padding = Insets(insets100)

    val defaultFont = new Font(font25)
    val titleFont = new Font(font30)
    val smallFont = new Font(font20)

    val titleLabel = new Label() {
      text = "New team:"
      font = titleFont
      padding = Insets(insets10)
    }
    val titleBox = new HBox() {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, columnRow0, columnRow0)
    GridPane.setConstraints(titleBox, columnRow0, columnRow0, columnRow2, columnRow1)

    val selectTeamLabel = new Label() {
      text = "Select existing team:"
      font = defaultFont
      padding = Insets(insets20)
    }
    val selectBox = new HBox() {
      children = selectTeamLabel
      alignment = Pos.Center
    }
    add(selectBox, columnRow0, columnRow1)

    val insertTeamLabel = new Label() {
      text = "Insert new team:"
      font = defaultFont
      padding = Insets(insets20)
    }
    val insertBox = new HBox() {
      children = insertTeamLabel
      alignment = Pos.Center
    }
    add(insertBox, columnRow1, columnRow1)

    val choices = ObservableBuffer("Team A", "Team B", "Team C")
    val teamChoice = new ComboBox[String] {
      maxWidth = widthHeight250
      editable = true
      items = choices
    }
    teamChoice.setStyle("-fx-font-size:25")
    add(teamChoice, columnRow0, columnRow2)

    val nameLabel = new Label() {
      text = "Name:"
      font = defaultFont
      padding = Insets(insets10)
    }
    val nameField = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val nameBox = new HBox() {
      children.addAll(nameLabel, nameField)
      alignment = Pos.CenterRight
    }
    add(nameBox, columnRow1, columnRow2)

    val districtLabel = new Label() {
      text = "District:"
      font = defaultFont
      padding = Insets(insets10)
    }
    val districtField = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val districtBox = new HBox() {
      children.addAll(districtLabel, districtField)
      alignment = Pos.CenterRight
    }
    add(districtBox, columnRow1, columnRow3)

    val IDLabel = new Label() {
      text = "ID:"
      font = defaultFont
      padding = Insets(insets10)
    }
    val IDField = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val IDBox = new HBox() {
      children.addAll(IDLabel, IDField)
      alignment = Pos.CenterRight
    }
    add(IDBox, columnRow1, columnRow4)

    val addButton = new Button {
      text = "Add"
      font = smallFont
      margin = Insets(insets10)
      prefWidth = widthHeight100
    }
    val addBox = new HBox {
      children = addButton
      alignment = Pos.CenterRight
    }
    add(addBox, columnRow1, columnRow5)

    val selectButton = new Button {
      text = "Select"
      font = defaultFont
      margin = Insets(insets30)
      prefWidth = widthHeight150
      onMouseClicked = (event: MouseEvent) => {
        newTeamController.selectPress()
      }
    }
    val cancelButton = new Button() {
      text = "Cancel"
      font = defaultFont
      margin = Insets(insets30)
      prefWidth = widthHeight150

      onMouseClicked = (event: MouseEvent) => {
        newTeamController.cancelPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(insets30)
      children.addAll(selectButton, cancelButton)
    }
    add(buttonBox, columnRow0, columnRow6)
    GridPane.setConstraints(buttonBox, columnRow0, columnRow6, columnRow2, columnRow1)
  }

  def grid = _grid

}
