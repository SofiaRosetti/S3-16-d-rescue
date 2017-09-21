package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.EnrollTeamControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class EnrollTeamGrid(private var enrollTeamController: EnrollTeamControllerImpl) {

  val _grid = new GridPane() {
    hgap = Gap
    vgap = Gap
    padding = Insets(Insets100)

    val defaultFont = new Font(Font25)
    val titleFont = new Font(Font30)
    val smallFont = new Font(Font20)

    val titleLabel = new Label() {
      text = "Enroll team:"
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

    val choices = enrollTeamController.obsBuffer
    val teamChoice = new ComboBox[String] {
      maxWidth = WidthHeight250
      editable = true
      items = choices
      //TODO fix the view of rescue teams
    }
    teamChoice.setStyle("-fx-font-size:25")
    add(teamChoice, ColumnRow0, ColumnRow2)

    val IDLabel = new Label() {
      text = "ID:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val IDField = new TextField() {
      promptText = "ID"
      font = defaultFont
    }
    val IDBox = new HBox() {
      children.addAll(IDLabel, IDField)
      alignment = Pos.CenterRight
    }
    add(IDBox, ColumnRow1, ColumnRow2)

    val nameLabel = new Label() {
      text = "Name:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val nameField = new TextField() {
      promptText = "Name"
      font = defaultFont
    }
    val nameBox = new HBox() {
      children.addAll(nameLabel, nameField)
      alignment = Pos.CenterRight
    }
    add(nameBox, ColumnRow1, ColumnRow3)

    val AddressLabel = new Label() {
      text = "Address:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val AddressField = new TextField() {
      promptText = "Address"
      font = defaultFont
    }
    val AddressBox = new HBox() {
      children.addAll(AddressLabel, AddressField)
      alignment = Pos.CenterRight
    }
    add(AddressBox, ColumnRow1, ColumnRow4)

    val PhoneLabel = new Label() {
      text = "Phone number:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    val PhoneField = new TextField() {
      promptText = "Phone number"
      font = defaultFont
    }
    val PhoneBox = new HBox() {
      children.addAll(PhoneLabel, PhoneField)
      alignment = Pos.CenterRight
    }
    add(PhoneBox, ColumnRow1, ColumnRow5)


    val addButton = new Button {
      text = "Add"
      font = smallFont
      margin = Insets(Insets10)
      prefWidth = WidthHeight100
      onMouseClicked = (event: MouseEvent) => {
        enrollTeamController.startChecks(IDField.getText(), nameField.getText(), AddressField.getText(), PhoneField.getText())
      }
    }
    val addBox = new HBox {
      children = addButton
      alignment = Pos.CenterRight
    }
    add(addBox, ColumnRow1, ColumnRow6)

    val selectButton = new Button {
      text = "Select"
      font = defaultFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight150
      onMouseClicked = (event: MouseEvent) => {
        enrollTeamController.selectPress()
      }
    }
    val cancelButton = new Button() {
      text = "Back"
      font = defaultFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight150

      onMouseClicked = (event: MouseEvent) => {
        enrollTeamController.backPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(Insets30)
      children.addAll(selectButton, cancelButton)
    }
    add(buttonBox, ColumnRow0, ColumnRow7)
    GridPane.setConstraints(buttonBox, ColumnRow0, ColumnRow7, ColumnRow2, ColumnRow1)
  }

  def grid = _grid

}
