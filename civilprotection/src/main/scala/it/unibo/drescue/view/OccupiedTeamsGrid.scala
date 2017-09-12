package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.OccupiedTeamsControllerImpl

import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class OccupiedTeamsGrid(private var occupiedTeamsController: OccupiedTeamsControllerImpl) {

  val grid = new GridPane {
    hgap = 10
    vgap = 10
    padding = Insets(100)

    val defaultFont = new Font(25)
    val titleFont = new Font(30)

    val titleLabel = new Label {
      text = "Occupied teams:"
      font = titleFont
      padding = Insets(20)
    }
    add(titleLabel, 0, 0)
    val titleBox = new HBox {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, 0, 0)

    val teamsList = new ListView[String] {
      items = ObservableBuffer("Rescue team 1", "Rescue team 2", "Rescue team 3")
      prefHeight = 100
    }
    teamsList.selectionModel().setSelectionMode(SelectionMode.Multiple)
    add(teamsList, 0, 1)

    val stopButton = new Button {
      text = "Stop rescue"
      font = defaultFont
      padding = Insets(5)
      margin = Insets(30)
      prefWidth = 150

      onMouseClicked = (event: MouseEvent) => {
        occupiedTeamsController.stopRescuePress()
      }

    }
    val cancelButton = new Button {
      text = "Cancel"
      font = defaultFont
      padding = Insets(5)
      margin = Insets(30)
      prefWidth = 150

      onMouseClicked = (event: MouseEvent) => {
        occupiedTeamsController.cancelPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(30)
      children.addAll(stopButton, cancelButton)
    }
    add(buttonBox, 0, 3)
  }

}
