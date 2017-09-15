package it.unibo.drescue.view

import javafx.scene.input.MouseEvent

import it.unibo.drescue.StringUtils
import it.unibo.drescue.controller.LoginControllerImpl
import it.unibo.drescue.view.ViewConstants._

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control._
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.Font

class LoginGrid(private var loginController: LoginControllerImpl) {

  val _grid = new GridPane() {
    hgap = ViewConstants.Gap
    vgap = Gap
    padding = Insets(Insets100)

    val defaultFont = new Font(Font20)

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
    add(usernameLabel, ColumnRow0, ColumnRow0)
    add(username, ColumnRow1, ColumnRow0)

    val passwordLabel = new Label() {
      text = "Password:"
      font = defaultFont
    }
    add(passwordLabel, ColumnRow0, ColumnRow1)
    add(password, ColumnRow1, ColumnRow1)

    val loginButton = new Button() {
      text = "Login"
      font = defaultFont
      onMouseClicked = (event: MouseEvent) => {
        checkInputs(username.getText, password.getText) match {
          case true => loginController.loginPress(username.getText, password.getText)
          case false => loginController.emptyLogin()
        }
      }
    }
    val buttonBox = new HBox() {
      children = loginButton
      alignment = Pos.Center
    }
    add(buttonBox, ColumnRow0, ColumnRow4)
    GridPane.setConstraints(buttonBox, ColumnRow0, ColumnRow4, ColumnRow2, ColumnRow1)
  }

  def grid = _grid

  def checkInputs(username: String, password: String): Boolean = {
    StringUtils.isAValidString(username) && StringUtils.isAValidString(password)
  }

}
