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
    hgap = gap
    vgap = gap
    padding = Insets(insets100)

    val defaultFont = new Font(font25)
    val titleFont = new Font(font30)

    val titleLabel = new Label() {
      text = "New rescue:"
      font = titleFont
      padding = Insets(font20)
    }
    val titleBox = new HBox() {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, columnRow0, columnRow0)
    GridPane.setConstraints(titleBox, columnRow0, columnRow0, columnRow3, columnRow1)

    val recTeamLabel = new Label() {
      text = "Recommended team:"
      font = defaultFont
      padding = Insets(insets10)
    }
    add(recTeamLabel, columnRow0, columnRow1)

    val recTeam = new Label() {
      text = "Team X"
      font = defaultFont
    }
    add(recTeam, columnRow2, columnRow1)

    val otherTeamLabel = new Label() {
      text = "Choose another team:"
      font = defaultFont
    }
    add(otherTeamLabel, columnRow0, columnRow2)

    val choices = ObservableBuffer("Team X", "Team Y", "Team Z")
    val teamChoice = new ComboBox[String] {
      maxWidth = widthHeight200
      editable = true
      items = choices
    }
    teamChoice.setStyle("-fx-font-size:25")
    add(teamChoice, columnRow2, columnRow2)

    val sendButton = new Button() {
      text = "Send"
      font = defaultFont
      padding = Insets(insets5)
      margin = Insets(insets30)
      prefWidth = widthHeight150
      onMouseClicked = (event: MouseEvent) => {
        newRescueController.sendPress()
      }
    }
    val cancelButton = new Button() {
      text = "Cancel"
      font = defaultFont
      padding = Insets(insets5)
      margin = Insets(insets30)
      prefWidth = widthHeight150
      onMouseClicked = (event: MouseEvent) => {
        newRescueController.cancelPress()
      }
    }
    val buttonBox = new HBox() {
      alignment = Pos.Center
      padding = Insets(insets30)
      children.addAll(sendButton, cancelButton)
    }
    add(buttonBox, columnRow0, columnRow3)
    GridPane.setConstraints(buttonBox, columnRow0, columnRow3, columnRow3, columnRow1)
  }

  def grid = _grid
}
