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

    val DefaultFont = new Font(Font20)

    val Username = new TextField() {
      promptText = "Username"
      font = DefaultFont
    }
    val Password = new PasswordField() {
      promptText = "Password"
      font = DefaultFont
    }

    val UsernameLabel = new Label() {
      text = "Username:"
      font = DefaultFont
    }
    add(UsernameLabel, ColumnRow0, ColumnRow0)
    add(Username, ColumnRow1, ColumnRow0)

    val PasswordLabel = new Label() {
      text = "Password:"
      font = DefaultFont
    }
    add(PasswordLabel, ColumnRow0, ColumnRow1)
    add(Password, ColumnRow1, ColumnRow1)

    val LoginButton = new Button() {
      text = "Login"
      font = DefaultFont
      onMouseClicked = (event: MouseEvent) => {
        checkInputs(Username.getText, Password.getText) match {
          case true => loginController.loginPress(Username.getText, Password.getText)
          case false => loginController.startEmptyLoginDialog()
        }
      }
    }
    val ButtonBox = new HBox() {
      children = LoginButton
      alignment = Pos.Center
    }
    add(ButtonBox, ColumnRow0, ColumnRow4)
    GridPane.setConstraints(ButtonBox, ColumnRow0, ColumnRow4, ColumnRow2, ColumnRow1)
  }

  def grid = _grid

  def checkInputs(username: String, password: String): Boolean = {
    StringUtils.isAValidString(username) && StringUtils.isAValidString(password)
  }

}
