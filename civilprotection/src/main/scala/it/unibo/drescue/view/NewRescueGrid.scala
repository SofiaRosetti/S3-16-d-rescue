package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.NewRescueControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class NewRescueGrid(private var newRescueController: NewRescueControllerImpl) {

  val _grid = new GridPane() {
    hgap = Gap
    vgap = Gap
    padding = Insets(Insets100)

    val defaultFont = new Font(Font25)
    val titleFont = new Font(Font30)

    val titleLabel = new Label() {
      text = "New rescue:"
      font = titleFont
      padding = Insets(Font20)
    }
    val titleBox = new HBox() {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, ColumnRow0, ColumnRow0)
    GridPane.setConstraints(titleBox, ColumnRow0, ColumnRow0, ColumnRow3, ColumnRow1)

    val recTeamLabel = new Label() {
      text = "Recommended team:"
      font = defaultFont
      padding = Insets(Insets10)
    }
    add(recTeamLabel, ColumnRow0, ColumnRow1)

    val recTeam = new Label() {
      text = "Team X"
      font = defaultFont
    }
    add(recTeam, ColumnRow2, ColumnRow1)

    val otherTeamLabel = new Label() {
      text = "Choose another team:"
      font = defaultFont
    }
    add(otherTeamLabel, ColumnRow0, ColumnRow2)

    val choices = ObservableBuffer("Team X", "Team Y", "Team Z")
    val teamChoice = new ComboBox[String] {
      maxWidth = WidthHeight200
      editable = true
      items = choices
    }
    teamChoice.setStyle("-fx-font-size:25")
    add(teamChoice, ColumnRow2, ColumnRow2)

    val sendButton = new Button() {
      text = "Send"
      font = defaultFont
      padding = Insets(Insets5)
      margin = Insets(Insets30)
      prefWidth = WidthHeight150
      onMouseClicked = (event: MouseEvent) => {
        newRescueController.sendPress()
      }
    }
    val cancelButton = new Button() {
      text = "Cancel"
      font = defaultFont
      padding = Insets(Insets5)
      margin = Insets(Insets30)
      prefWidth = WidthHeight150
      onMouseClicked = (event: MouseEvent) => {
        newRescueController.cancelPress()
      }
    }
    val buttonBox = new HBox() {
      alignment = Pos.Center
      padding = Insets(Insets30)
      children.addAll(sendButton, cancelButton)
    }
    add(buttonBox, ColumnRow0, ColumnRow3)
    GridPane.setConstraints(buttonBox, ColumnRow0, ColumnRow3, ColumnRow3, ColumnRow1)
  }

  def grid = _grid
}
