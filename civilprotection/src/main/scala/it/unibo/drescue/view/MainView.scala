package it.unibo.drescue.view

import it.unibo.drescue.controller._

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

class MainView(login: LoginGrid,
               controller: MainControllerImpl,
               homeController: HomeControllerImpl,
               newRescueController: NewRescueControllerImpl,
               newTeamController: NewTeamControllerImpl,
               occupiedTeamsController: OccupiedTeamsControllerImpl) extends JFXApp {

  var home = new HomeGrid(homeController)
  var rescue = new NewRescueGrid(newRescueController)
  var team = new NewTeamGrid(newTeamController)
  var occTeams = new OccupiedTeamsGrid(occupiedTeamsController)

  def setStage(): Unit = {
    stage = new PrimaryStage {
      title = "D-rescue"
      resizable = false
      scene = new Scene {
        content = login.grid
      }
    }
  }

  def changeView(view: String): Unit = {

    val HomeCase: String = "Home"
    val RescueCase: String = "NewRescue"
    val TeamCase: String = "NewTeam"
    val OccTeamsCase: String = "OccupiedTeams"

    var newScene = new Scene {

      view match {
        case HomeCase => {
          home = new HomeGrid(homeController)
          content = home.grid
        }
        case RescueCase => {
          rescue = new NewRescueGrid(newRescueController)
          content = rescue.grid
        }
        case TeamCase => {
          team = new NewTeamGrid(newTeamController)
          content = team.grid
        }
        case OccTeamsCase => {
          occTeams = new OccupiedTeamsGrid(occupiedTeamsController)
          content = occTeams.grid
        }
        case _ => println("error") // TODO throw and handle exception
      }
    }

    stage_.hide()
    stage_.scene_=(newScene)
    stage_.centerOnScreen()
    stage_.show()
  }

  def stage_ = stage
}
