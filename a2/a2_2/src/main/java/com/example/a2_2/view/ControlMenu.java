package com.example.a2_2.view;

import java.util.HashMap;

import com.example.a2_2.Controller;
import com.example.a2_2.model.Ant;
import com.example.a2_2.model.GridPosition;
import com.example.a2_2.model.PublishSubscribe;
import com.example.a2_2.view.EnvironmentTile.TileType;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class ControlMenu extends VBox implements PublishSubscribe {

  private AntNumberEntry antNumberEntry;
  private GridDimensionToggle gridDimensionToggle;
  private SimulationControlButtons controlButtons;

  public ControlMenu(Controller controller) {

    antNumberEntry = new AntNumberEntry(controller);
    gridDimensionToggle = new GridDimensionToggle(controller);
    controlButtons = new SimulationControlButtons(controller);

    getChildren().addAll(antNumberEntry, gridDimensionToggle, controlButtons);
    setAlignment(Pos.CENTER);
  }
  
  @Override
  public void update(
      double windowSize,
      boolean simulationStarted,
      boolean animationPlaying,
      TileType[][] environmentGrid,
      HashMap<GridPosition, Ant> antPositionMap, 
      int numberAnts) {

    setPrefHeight(windowSize);
    setSpacing(getPrefHeight() / 5);

    int maxNumberAnts = (int) (Math.pow(environmentGrid.length, 2) * 0.333);
    boolean inputValid = numberAnts > 0 && numberAnts <= maxNumberAnts;

    antNumberEntry.update(windowSize, maxNumberAnts, simulationStarted);
    gridDimensionToggle.update(windowSize, simulationStarted);
    controlButtons.update(windowSize, inputValid, simulationStarted, animationPlaying);
  }
}
