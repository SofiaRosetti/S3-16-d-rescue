package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

object RescueView extends JFXApp {

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
          text = "New rescue:"
          font = titleFont
          padding = Insets(20)
        }
        val titleBox = new HBox {
          children = titleLabel
          alignment = Pos.Center
        }
        add(titleBox, 0, 0)
        GridPane.setConstraints(titleBox, 0, 0, 3, 1)

        val recTeamLabel = new Label {
          text = "Recommended team:"
          font = defaultFont
          padding = Insets(10)
        }
        add(recTeamLabel, 0, 1)

        val recTeam = new Label {
          text = "Team X"
          font = defaultFont
        }
        add(recTeam, 2, 1)

        val otherTeamLabel = new Label {
          text = "Choose another team:"
          font = defaultFont
        }
        add(otherTeamLabel, 0, 2)

        val choices = ObservableBuffer("Team X", "Team Y", "Team Z")
        val teamChoice = new ComboBox[String] {
          maxWidth = 200
          editable = true
          items = choices
        }
        teamChoice.setStyle("-fx-font-size:25")
        add(teamChoice, 2, 2)

        val sendButton = new Button {
          text = "Send"
          font = defaultFont
          padding = Insets(5)
          margin = Insets(30)
          prefWidth = 150
        }

        val cancelButton = new Button {
          text = "Cancel"
          font = defaultFont
          padding = Insets(5)
          margin = Insets(30)
          prefWidth = 150
        }

        val buttonBox = new HBox {
          alignment = Pos.Center
          padding = Insets(30)
          children.addAll(sendButton, cancelButton)
        }
        add(buttonBox, 0, 3)
        GridPane.setConstraints(buttonBox, 0, 3, 3, 1)
      }
      content = grid
    }
  }
}
