package com.example.a2_1.view;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.TileSelector.TileSelectorType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlMenu extends VBox implements PublishSubscribe {
  
  private Controller controller;

  public ControlMenu(Controller controller) {
    this.controller = controller;
    setAlignment(Pos.CENTER);
    // setStyle("-fx-background-color: red;");
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

    getChildren().clear();

    setPrefHeight(gridHeight * 0.2);
    setSpacing(getPrefHeight() * 0.1);

    StartResetButton button = new StartResetButton(0, animationStarted);
    button.setOnMouseClicked(controller::handleMouseClicked);

    VBox pathBox = new VBox();
    
    Label pathLabel = new Label("Length");
    String pathString = "";
    switch (pathLength) {
      case 0 -> pathString = "N/A";
      case -1 -> pathString = "None";
      case -2 -> pathString = "...";
      default -> pathString = Integer.toString(pathLength);
    }
    Label pathNumber = new Label(pathString);
    
    pathBox.setAlignment(Pos.CENTER);
    pathBox.getChildren().addAll(pathLabel, pathNumber);

    getChildren().addAll(button, pathBox);
  }
}
