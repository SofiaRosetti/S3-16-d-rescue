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
    hgap = Gap5
    vgap = Gap5
    padding = Insets(Insets30)

    val DefaultFont = new Font(Font25)
    val TitleFont = new Font(Font30)
    val SmallFont = new Font(Font20)

    val TitleLabel = new Label() {
      text = "Enroll team:"
      font = TitleFont
      padding = Insets(Insets10)
    }
    val TitleBox = new HBox() {
      children = TitleLabel
      alignment = Pos.Center
    }
    add(TitleBox, ColumnRow0, ColumnRow0)
    GridPane.setConstraints(TitleBox, ColumnRow0, ColumnRow0, ColumnRow2, ColumnRow1)

    val SelectTeamLabel = new Label() {
      text = "Select existing team:"
      font = DefaultFont
      padding = Insets(Insets20)
    }
    val SelectBox = new HBox() {
      children = SelectTeamLabel
      alignment = Pos.Center
    }
    add(SelectBox, ColumnRow0, ColumnRow1)

    val InsertTeamLabel = new Label() {
      text = "Insert new team:"
      font = DefaultFont
      padding = Insets(Insets20)
    }
    val InsertBox = new HBox() {
      children = InsertTeamLabel
      alignment = Pos.Center
    }
    add(InsertBox, ColumnRow1, ColumnRow1)

    val Choices = enrollTeamController.obsBuffer
    val TeamChoice = new ComboBox[String] {
      maxWidth = WidthHeight250
      editable = true
      items = Choices
    }
    TeamChoice.setStyle("-fx-font-size:25")
    add(TeamChoice, ColumnRow0, ColumnRow2)

    val IDLabel = new Label() {
      text = "ID:"
      font = DefaultFont
      padding = Insets(Insets10)
    }
    val IDField = new TextField() {
      promptText = "ID"
      font = DefaultFont
    }
    val IDBox = new HBox() {
      children.addAll(IDLabel, IDField)
      alignment = Pos.CenterRight
    }
    add(IDBox, ColumnRow1, ColumnRow2)

    val NameLabel = new Label() {
      text = "Name:"
      font = DefaultFont
      padding = Insets(Insets10)
    }
    val NameField = new TextField() {
      promptText = "Name"
      font = DefaultFont
    }
    val NameBox = new HBox() {
      children.addAll(NameLabel, NameField)
      alignment = Pos.CenterRight
    }
    add(NameBox, ColumnRow1, ColumnRow3)

    val AddressLabel = new Label() {
      text = "Address:"
      font = DefaultFont
      padding = Insets(Insets10)
    }
    val AddressField = new TextField() {
      promptText = "Address"
      font = DefaultFont
    }
    val AddressBox = new HBox() {
      children.addAll(AddressLabel, AddressField)
      alignment = Pos.CenterRight
    }
    add(AddressBox, ColumnRow1, ColumnRow4)

    val PhoneLabel = new Label() {
      text = "Phone number:"
      font = DefaultFont
      padding = Insets(Insets10)
    }
    val PhoneField = new TextField() {
      promptText = "Phone number"
      font = DefaultFont
    }
    val PhoneBox = new HBox() {
      children.addAll(PhoneLabel, PhoneField)
      alignment = Pos.CenterRight
    }
    add(PhoneBox, ColumnRow1, ColumnRow5)

    val AddButton = new Button {
      text = "Add"
      font = SmallFont
      margin = Insets(Insets10)
      prefWidth = WidthHeight100
      onMouseClicked = (event: MouseEvent) => {
        enrollTeamController.startChecks(IDField.getText(), NameField.getText(), AddressField.getText(), PhoneField.getText())
      }
    }
    val AddBox = new HBox {
      children = AddButton
      alignment = Pos.CenterRight
    }
    add(AddBox, ColumnRow1, ColumnRow6)

    val SelectButton = new Button {
      text = "Select"
      font = DefaultFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight150
      onMouseClicked = (event: MouseEvent) => {
        var selectedTeamID = TeamChoice.getValue
        println("SelectButton" + selectedTeamID)
        enrollTeamController.selectPress(selectedTeamID)
      }
    }
    val CancelButton = new Button() {
      text = "Back"
      font = DefaultFont
      margin = Insets(Insets30)
      prefWidth = WidthHeight150

      onMouseClicked = (event: MouseEvent) => {
        enrollTeamController.backPress()
      }
    }
    val ButtonBox = new HBox {
      alignment = Pos.Center
      //padding = Insets(Insets10)
      children.addAll(SelectButton, CancelButton)
    }
    add(ButtonBox, ColumnRow0, ColumnRow7)
    GridPane.setConstraints(ButtonBox, ColumnRow0, ColumnRow7, ColumnRow2, ColumnRow1)
  }

  def grid = _grid

}
