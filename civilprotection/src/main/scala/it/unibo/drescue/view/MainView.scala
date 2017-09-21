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
               newRescueController: NewRescueControllerImpl,
               enrollTeamControllerImpl: EnrollTeamControllerImpl,
               occupiedTeamsController: OccupiedTeamsControllerImpl,
               manageRescuesController: ManageRescuesControllerImpl) extends JFXApp {

  var login = new LoginGrid(loginController)
  var home = new HomeGrid(homeController)
  var rescue = new NewRescueGrid(newRescueController)
  var team = new EnrollTeamGrid(enrollTeamControllerImpl)
  var occTeams = new OccupiedTeamsGrid(occupiedTeamsController)
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
    val RescueCase: String = "NewRescue"
    val TeamCase: String = "NewTeam"
    val OccTeamsCase: String = "OccupiedTeams"
    val ManageRescuesCase: String = "ManageRescues"

    var newScene = new Scene {
      // TODO remove not used case
      view match {
        case LoginCase => {
          login = new LoginGrid(loginController)
          content = login.grid
        }
        case HomeCase => {
          homeController.startAlertsRequest()
          home = new HomeGrid(homeController)
          content = home.grid
        }
        case RescueCase => {
          rescue = new NewRescueGrid(newRescueController)
          content = rescue.grid
        }
        case TeamCase => {
          team = new EnrollTeamGrid(enrollTeamControllerImpl)
          content = team.grid
        }
        case OccTeamsCase => {
          occTeams = new OccupiedTeamsGrid(occupiedTeamsController)
          content = occTeams.grid
        }
        case ManageRescuesCase => {
          manage = new ManageRescuesGrid(manageRescuesController)
          content = manage.grid
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
