package it.unibo.drescue

import it.unibo.drescue.controller._
import it.unibo.drescue.model.CivilProtectionImpl
import it.unibo.drescue.view.{LoginGrid, MainView}

import scalafx.application.JFXApp

object Main extends JFXApp {

  var controller = new MainControllerImpl(null)
  var loginController = new LoginControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var homeController = new HomeControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var newRescueController = new NewRescueControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var newTeamController = new NewTeamControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var occupiedTeamsController = new OccupiedTeamsControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)

  var loginGrid = new LoginGrid(loginController)

  var view = new MainView(
    controller = controller,
    login = loginGrid,
    homeController = homeController,
    newRescueController = newRescueController,
    newTeamController = newTeamController,
    occupiedTeamsController = occupiedTeamsController)

  controller.addView(view)
  view setStage()
  stage = view.stage_
}
