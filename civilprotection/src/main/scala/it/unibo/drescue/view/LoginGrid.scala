package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.LoginControllerImpl

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, PasswordField, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class LoginGrid(private var loginController: LoginControllerImpl) {

  val _grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(100, 100, 100, 100)

    val defaultFont = new Font(20)

    val username = new TextField() {
      promptText = "Username"
      font = defaultFont
    }
    val password = new PasswordField() {
      promptText = "Password"
      font = defaultFont
    }

    val usernameLabel = new Label() {
      text = "Username:"
      font = defaultFont
    }
    add(usernameLabel, 0, 0)
    add(username, 1, 0)

    val passwordLabel = new Label() {
      text = "Password:"
      font = defaultFont
    }
    add(passwordLabel, 0, 1)
    add(password, 1, 1)

    val loginButton = new Button() {
      text = "Login"
      font = defaultFont
      onMouseClicked = (event: MouseEvent) => {
        loginController.loginPress(username.getText, password.getText)
      }
    }
    val buttonBox = new HBox() {
      children = loginButton
      alignment = Pos.Center
    }
    add(buttonBox, 0, 4)
    GridPane.setConstraints(buttonBox, 0, 4, 2, 1)
  }

  def grid = _grid

}
