package it.unibo.drescue

import it.unibo.drescue.connection.{RabbitMQConnectionImpl, RabbitMQImpl}
import it.unibo.drescue.controller._
import it.unibo.drescue.model.CivilProtectionImpl
import it.unibo.drescue.view.{LoginGrid, MainView}

import scalafx.application.JFXApp

object Main extends JFXApp {

  val connection = new RabbitMQConnectionImpl("localhost")
  connection.openConnection()
  val loginChannel: RabbitMQImpl = new RabbitMQImpl(connection)
  val replyQueue = loginChannel.addReplyQueue()
  val props = loginChannel.setReplyTo(replyQueue)
  var controller = new MainControllerImpl(null)
  var loginController = new LoginControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller, loginChannel)
  var homeController = new HomeControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var newRescueController = new NewRescueControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var enrollTeamController = new EnrollTeamControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)
  var occupiedTeamsController = new OccupiedTeamsControllerImpl(new CivilProtectionImpl("prova", "prova") :: Nil, controller)

  var loginGrid = new LoginGrid(loginController)

  var view = new MainView(
    loginController = loginController,
    controller = controller,
    loginGrid = loginGrid,
    homeController = homeController,
    newRescueController = newRescueController,
    enrollTeamControllerImpl = enrollTeamController,
    occupiedTeamsController = occupiedTeamsController)

  controller.addView(view)
  view setStage()
  stage = view._stage
}
