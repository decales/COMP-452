package com.example.a2_1.view;

import javax.swing.GroupLayout.Alignment;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.TileSelector.TileSelectorType;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
      TileSelectorType currentTileType) {

    getChildren().clear();

    setPrefHeight(gridHeight * 0.2);
    setSpacing(getPrefHeight() * 0.1);

    Button startButton = new Button("Start");
    Button resetButton = new Button("Reset");

    VBox pathBox = new VBox();
    Label pathLabel = new Label("Length");
    Label pathNumber = new Label("0");
    pathBox.setAlignment(Pos.CENTER);
    pathBox.getChildren().addAll(pathLabel, pathNumber);

    getChildren().addAll(startButton, resetButton, pathBox);
  }
}
