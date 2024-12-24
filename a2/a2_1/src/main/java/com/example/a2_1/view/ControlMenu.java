package com.example.a2_1.view;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.TileSelector.TileSelectorType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class ControlMenu extends VBox implements PublishSubscribe {
  
  private StartResetButton button;
  private Label pathValue;

  public ControlMenu(Controller controller) {
    
    VBox pathBox = new VBox();
    Label pathLabel = new Label("Length");
    
    button = new StartResetButton(0);
    button.setOnMouseClicked(controller::handleMouseClicked);

    pathValue = new Label();
    pathBox.setAlignment(Pos.CENTER);
    pathBox.getChildren().addAll(pathLabel, pathValue);

    getChildren().addAll(button, pathBox);
    setAlignment(Pos.CENTER);
  }

  @Override
  public void update(
      double gridHeight,
      int[][] terrainGrid,
      int[][] entityGrid,
      int[][] visited,
      TileSelectorType currentTileType,
      boolean animationStarted,
      int pathLength) {

    setPrefHeight(gridHeight * 0.2);
    setSpacing(getPrefHeight() * 0.1);

    button.setMode(animationStarted);
    
    String pathString = "";
    switch (pathLength) {
      case 0 -> pathString = "N/A";
      case -1 -> pathString = "None";
      case -2 -> pathString = "...";
      default -> pathString = Integer.toString(pathLength);
    }
    pathValue.setText(pathString);
  }
}
