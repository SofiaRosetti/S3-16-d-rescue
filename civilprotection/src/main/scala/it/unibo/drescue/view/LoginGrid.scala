package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.controller.LoginControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, PasswordField, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class LoginGrid(private var loginController: LoginControllerImpl) {

  val _grid = new GridPane() {
    hgap = ViewConstants.gap
    vgap = gap
    padding = Insets(insets100)

    val defaultFont = new Font(font20)

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
    add(usernameLabel, columnRow0, columnRow0)
    add(username, columnRow1, columnRow0)

    val passwordLabel = new Label() {
      text = "Password:"
      font = defaultFont
    }
    add(passwordLabel, columnRow0, columnRow1)
    add(password, columnRow1, columnRow1)

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
    add(buttonBox, columnRow0, columnRow4)
    GridPane.setConstraints(buttonBox, columnRow0, columnRow4, columnRow2, columnRow1)
  }

  def grid = _grid

}
