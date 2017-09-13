package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.OccupiedTeamsControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class OccupiedTeamsGrid(private var occupiedTeamsController: OccupiedTeamsControllerImpl) {

  val grid = new GridPane {
    hgap = gap
    vgap = gap
    padding = Insets(insets100)

    val defaultFont = new Font(font25)
    val titleFont = new Font(font30)

    val titleLabel = new Label {
      text = "Occupied teams:"
      font = titleFont
      padding = Insets(insets20)
    }
    val titleBox = new HBox {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, columnRow0, columnRow0)

    val teamsList = new ListView[String] {
      items = ObservableBuffer("Rescue team 1", "Rescue team 2", "Rescue team 3")
      prefHeight = widthHeight100
    }
    teamsList.selectionModel().setSelectionMode(SelectionMode.Multiple)
    add(teamsList, columnRow0, columnRow1)

    val stopButton = new Button {
      text = "Stop rescue"
      font = defaultFont
      padding = Insets(insets5)
      margin = Insets(insets30)
      prefWidth = widthHeight150

      onMouseClicked = (event: MouseEvent) => {
        occupiedTeamsController.stopRescuePress()
      }

    }
    val cancelButton = new Button {
      text = "Cancel"
      font = defaultFont
      padding = Insets(insets5)
      margin = Insets(insets30)
      prefWidth = widthHeight150

      onMouseClicked = (event: MouseEvent) => {
        occupiedTeamsController.cancelPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(insets30)
      children.addAll(stopButton, cancelButton)
    }
    add(buttonBox, columnRow0, columnRow3)
  }

}
