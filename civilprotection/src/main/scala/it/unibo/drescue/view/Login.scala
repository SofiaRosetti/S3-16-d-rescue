package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Label, PasswordField, TextField}
import scalafx.scene.layout.GridPane
import scalafx.scene.text.Font

object Login extends JFXApp {

  stage = new PrimaryStage {
    title = "D-rescue"
    scene = new Scene {
      val grid = new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(100)

        val defaultFont = new Font(20)

        val usernameLabel = new Label {
          text = "Username:"
          font = defaultFont
        }
        add(usernameLabel, 0, 0)

        val passwordLabel = new Label {
          text = "Password:"
          font = defaultFont
        }
        add(passwordLabel, 0, 1)

        val username = new TextField {
          promptText = "Username"
          font = defaultFont
        }
        add(username, 1, 0)

        val password = new PasswordField {
          promptText = "Password"
          font = defaultFont
        }
        add(password, 1, 1)

      }
      content = grid
    }
  }

}