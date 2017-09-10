package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

object TeamView extends JFXApp {

  stage = new PrimaryStage {
    title = "D-rescue"
    scene = new Scene {
      val grid = new GridPane {
        resizable = false
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

        val choices = ObservableBuffer("Team X", "Team Y", "Team Z")
        val teamChoice = new ComboBox[String] {
          maxWidth = 250
          editable = true
          items = choices
        }
        teamChoice.setStyle("-fx-font-size:25")
        add(teamChoice, 0, 2)

        val nameLabel = new Label {
          text = "Name:"
          font = defaultFont
          padding = Insets(10)
        }
        val nameField = new TextField {
          promptText = "Name"
          font = defaultFont
        }
        val nameBox = new HBox {
          children.addAll(nameLabel, nameField)
          alignment = Pos.CenterRight
        }
        add(nameBox, 1, 2)

        val districtLabel = new Label {
          text = "District:"
          font = defaultFont
          padding = Insets(10)
        }
        val districtField = new TextField {
          promptText = "District"
          font = defaultFont
        }
        val districtBox = new HBox {
          children.addAll(districtLabel, districtField)
          alignment = Pos.CenterRight
        }
        add(districtBox, 1, 3)

        val IDLabel = new Label {
          text = "ID:"
          font = defaultFont
          padding = Insets(10)
        }
        val IDField = new TextField {
          promptText = "ID"
          font = defaultFont
        }
        val IDBox = new HBox {
          children.addAll(IDLabel, IDField)
          alignment = Pos.CenterRight
        }
        add(IDBox, 1, 4)

        val addButton = new Button {
          text = "Add"
          font = defaultFont
          margin = Insets(30)
          prefWidth = 150
        }
        val cancelButton = new Button {
          text = "Cancel"
          font = defaultFont
          margin = Insets(30)
          prefWidth = 150
        }
        val buttonBox = new HBox {
          alignment = Pos.Center
          padding = Insets(30)
          children.addAll(addButton, cancelButton)
        }
        add(buttonBox, 0, 5)
        GridPane.setConstraints(buttonBox, 0, 5, 2, 1)

      }
      content = grid
    }
  }
}
