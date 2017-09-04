package it.unibo.drescue.view

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import scalafx.scene.text.Font

object TeamView extends JFXApp {

  stage = new PrimaryStage {
    title = "D-rescue"
    scene = new Scene {
      val grid = new GridPane {
        hgap = 10
        vgap = 10
        padding = Insets(100)

        val defaultFont = new Font(25)
        val titleFont = new Font(30)

      }
      content = grid
    }
  }
}
