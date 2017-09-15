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
    hgap = Gap
    vgap = Gap
    padding = Insets(Insets100)

    val defaultFont = new Font(Font25)
    val titleFont = new Font(Font30)

    val titleLabel = new Label {
      text = "Occupied teams:"
      font = titleFont
      padding = Insets(Insets20)
    }
    val titleBox = new HBox {
      children = titleLabel
      alignment = Pos.Center
    }
    add(titleBox, ColumnRow0, ColumnRow0)

    val teamsList = new ListView[String] {
      items = ObservableBuffer("Rescue team 1", "Rescue team 2", "Rescue team 3")
      prefHeight = WidthHeight100
    }
    teamsList.selectionModel().setSelectionMode(SelectionMode.Multiple)
    add(teamsList, ColumnRow0, ColumnRow1)

    val stopButton = new Button {
      text = "Stop rescue"
      font = defaultFont
      padding = Insets(Insets5)
      margin = Insets(Insets30)
      prefWidth = WidthHeight150

      onMouseClicked = (event: MouseEvent) => {
        occupiedTeamsController.stopRescuePress()
      }

    }
    val cancelButton = new Button {
      text = "Cancel"
      font = defaultFont
      padding = Insets(Insets5)
      margin = Insets(Insets30)
      prefWidth = WidthHeight150

      onMouseClicked = (event: MouseEvent) => {
        occupiedTeamsController.cancelPress()
      }
    }
    val buttonBox = new HBox {
      alignment = Pos.Center
      padding = Insets(Insets30)
      children.addAll(stopButton, cancelButton)
    }
    add(buttonBox, ColumnRow0, ColumnRow3)
  }

}
