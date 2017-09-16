package it.unibo.drescue.view

import it.unibo.drescue.controller._

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

class MainView(loginGrid: LoginGrid,
               loginController: LoginControllerImpl,
               controller: MainControllerImpl,
               homeController: HomeControllerImpl,
               newRescueController: NewRescueControllerImpl,
               newTeamController: NewTeamControllerImpl,
               occupiedTeamsController: OccupiedTeamsControllerImpl) extends JFXApp {

  var login = new LoginGrid(loginController)
  var home = new HomeGrid(homeController)
  var rescue = new NewRescueGrid(newRescueController)
  var team = new EnrollTeamGrid(newTeamController)
  var occTeams = new OccupiedTeamsGrid(occupiedTeamsController)

  def setStage(): Unit = {
    stage = new PrimaryStage {
      title = "D-rescue"
      resizable = false
      scene = new Scene {
        content = loginGrid.grid
      }
    }
  }

  def changeView(view: String): Unit = {

    val LoginCase: String = "Login"
    val HomeCase: String = "Home"
    val RescueCase: String = "NewRescue"
    val TeamCase: String = "NewTeam"
    val OccTeamsCase: String = "OccupiedTeams"

    var newScene = new Scene {

      view match {
        case LoginCase => {
          login = new LoginGrid(loginController)
          content = login.grid
        }
        case HomeCase => {
          home = new HomeGrid(homeController)
          content = home.grid
        }
        case RescueCase => {
          rescue = new NewRescueGrid(newRescueController)
          content = rescue.grid
        }
        case TeamCase => {
          team = new EnrollTeamGrid(newTeamController)
          content = team.grid
        }
        case OccTeamsCase => {
          occTeams = new OccupiedTeamsGrid(occupiedTeamsController)
          content = occTeams.grid
        }
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
