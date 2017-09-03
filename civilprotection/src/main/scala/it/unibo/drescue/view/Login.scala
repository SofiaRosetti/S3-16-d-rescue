package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane

object Login extends JFXApp {

  stage = new PrimaryStage {
    title = "D-rescue"
    scene = new Scene {
      val grid = new GridPane() {
        hgap = 10
        vgap = 10
        padding = Insets(100)
      }
      content = grid
    }
  }

}