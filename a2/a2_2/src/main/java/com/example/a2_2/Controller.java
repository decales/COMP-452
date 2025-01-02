package com.example.a2_2;

import com.example.a2_2.model.Model;
import com.example.a2_2.view.AntNumberEntry;
import com.example.a2_2.view.GridDimensionToggle;
import com.example.a2_2.view.SimulationControlButtons;
import com.example.a2_2.view.SimulationControlButtons.ButtonMode;

import javafx.scene.Node;

public class Controller {

  private Model model;

  public Controller(Model model) {
    this.model = model;
  }

  public void onValueChange(Node node) {
    
    switch (node) {
      case AntNumberEntry antNumberEntry -> model.setNumberAnts(antNumberEntry.getInputValue());
      case GridDimensionToggle GridDimensionToggle -> model.initializeGrid(GridDimensionToggle.getSelectionValue());
      case SimulationControlButtons controlButtons -> {
        switch(controlButtons.modeOfSelected) {
          case ButtonMode.Start -> model.startSimulation();  
          case ButtonMode.Reset -> model.resetSimulation();  
          case ButtonMode.Play -> model.playAnimation();  
          case ButtonMode.Pause -> model.pauseAnimation();  
        }
      }
      default -> {}
    }
  }
}
