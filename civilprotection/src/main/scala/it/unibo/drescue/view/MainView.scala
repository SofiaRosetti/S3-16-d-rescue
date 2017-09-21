package it.unibo.drescue.view

import javafx.stage.WindowEvent

import it.unibo.drescue.controller._

import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene

class MainView(loginGrid: LoginGrid,
               loginController: LoginControllerImpl,
               controller: MainControllerImpl,
               homeController: HomeControllerImpl,
               enrollTeamControllerImpl: EnrollTeamControllerImpl,
               manageRescuesController: ManageRescuesControllerImpl) extends JFXApp {

  var login = new LoginGrid(loginController)
  var home = new HomeGrid(homeController)
  var team = new EnrollTeamGrid(enrollTeamControllerImpl)
  var manage = new ManageRescuesGrid(manageRescuesController)

  def setStage(): Unit = {
    stage = new PrimaryStage {
      title = "D-rescue"
      resizable = false
      scene = new Scene {
        content = loginGrid.grid
      }
      onCloseRequest_=((event: WindowEvent) => {
        event.consume()
        Platform.exit()
        System.exit(0)
      })
    }
  }

  def changeView(view: String): Unit = {

    val LoginCase: String = "Login"
    val HomeCase: String = "Home"
    val EnrollCase: String = "NewTeam"
    val ManageRescuesCase: String = "ManageRescues"

    val newScene = new Scene {
      view match {
        case LoginCase =>
          login = new LoginGrid(loginController)
          content = login.grid
        case HomeCase =>
          home = new HomeGrid(homeController)
          content = home.grid
        case EnrollCase =>
          team = new EnrollTeamGrid(enrollTeamControllerImpl)
          content = team.grid
        case ManageRescuesCase =>
          manage = new ManageRescuesGrid(manageRescuesController)
          content = manage.grid
        case _ => println("error") // TODO throw and handle exception
      }
    }

    _stage.hide()
    _stage.scene_=(newScene)
    _stage.centerOnScreen()
    _stage.show()
  }

  def _stage = stage
}
