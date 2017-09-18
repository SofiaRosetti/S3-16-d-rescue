package it.unibo.drescue

import it.unibo.drescue.connection.{RabbitMQConnectionImpl, RabbitMQImpl}
import it.unibo.drescue.controller._
import it.unibo.drescue.localModel.CivilProtectionData
import it.unibo.drescue.view.{LoginGrid, MainView}

import scalafx.application.JFXApp

object Main extends JFXApp {

  val connection = new RabbitMQConnectionImpl("localhost")
  connection.openConnection()
  var cpData = CivilProtectionData()
  var controller = new MainControllerImpl(cpData, new RabbitMQImpl(connection))
  var loginController = new LoginControllerImpl(controller, new RabbitMQImpl(connection))
  var homeController = new HomeControllerImpl(controller)
  var newRescueController = new NewRescueControllerImpl(controller)
  var enrollTeamController = new EnrollTeamControllerImpl(controller)
  var occupiedTeamsController = new OccupiedTeamsControllerImpl(controller)
  var manageRescuesController = new ManageRescuesControllerImpl(controller)

  var loginGrid = new LoginGrid(loginController)

  var view = new MainView(
    loginController = loginController,
    controller = controller,
    loginGrid = loginGrid,
    homeController = homeController,
    newRescueController = newRescueController,
    enrollTeamControllerImpl = enrollTeamController,
    occupiedTeamsController = occupiedTeamsController,
    manageRescuesController = manageRescuesController)

  controller.addView(view)
  view setStage()
  stage = view._stage
}
