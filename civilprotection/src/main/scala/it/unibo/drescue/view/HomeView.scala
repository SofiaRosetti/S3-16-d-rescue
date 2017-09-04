package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane

object HomeView extends JFXApp {

  stage = new PrimaryStage {
    title = "D-rescue"
    scene = new Scene {
      val grid = new GridPane() {
        resizable = false
        hgap = 10
        vgap = 10
        padding = Insets(50)
      }
      content = grid
    }
  }

}
