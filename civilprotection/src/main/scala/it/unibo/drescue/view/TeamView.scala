package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

object TeamView extends JFXApp {

  stage = new PrimaryStage {
    title = "D-rescue"
    scene = new Scene {
      val grid = new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(100)

        val defaultFont = new Font(25)
        val titleFont = new Font(30)

        val titleLabel = new Label {
          text = "New team:"
          font = titleFont
          padding = Insets(10)
        }
        val titleBox = new HBox {
          children = titleLabel
          alignment = Pos.Center
        }
        add(titleBox, 0, 0)
        GridPane.setConstraints(titleBox, 0, 0, 2, 1)

        val selectTeamLabel = new Label {
          text = "Select existing team:"
          font = defaultFont
          padding = Insets(20)
        }
        val selectBox = new HBox {
          children = selectTeamLabel
          alignment = Pos.Center
        }
        add(selectBox, 0, 1)

        val insertTeamLabel = new Label {
          text = "Insert new team:"
          font = defaultFont
          padding = Insets(20)
        }
        val insertBox = new HBox {
          children = insertTeamLabel
          alignment = Pos.Center
        }
        add(insertBox, 1, 1)

      }
      content = grid
    }
  }
}
