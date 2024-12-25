package com.example.a2_1.view;

import java.util.List;
import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.TileSelector.TileSelectorType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class ControlMenu extends VBox implements PublishSubscribe {
  
  private Button clearButton;
  private VBox animationSpeedBox;
  private StartResetButton startButton;
  private Label pathValue;

  public ControlMenu(Controller controller) {

    // Clear Button
    clearButton = new Button("Clear");
    clearButton.setOnMouseClicked(controller::handleMouseClicked);
    
    // Animation speed control
    animationSpeedBox = new VBox();
    animationSpeedBox.setAlignment(Pos.CENTER);
    animationSpeedBox.getChildren().add(new Label("Speed"));

    ToggleGroup animationSpeedSelect = new ToggleGroup();
    VBox radioButtonBox = new VBox();
    radioButtonBox.setAlignment(Pos.CENTER_LEFT);

    for (int i : List.of(1, 4, 8, 16)) {
      RadioButton rbutton = new RadioButton(Integer.toString(i));
      rbutton.setToggleGroup(animationSpeedSelect);
      if (i == 8) rbutton.setSelected(true);
      rbutton.setOnMouseClicked(controller::handleMouseClicked);
      radioButtonBox.getChildren().add(rbutton);
    }
    animationSpeedBox.getChildren().add(radioButtonBox);

    // Start/reset button
    startButton = new StartResetButton(0);
    startButton.setOnMouseClicked(controller::handleMouseClicked);

    // Path length box
    VBox pathBox = new VBox();
    Label pathLabel = new Label("Length");
    pathValue = new Label();
    pathBox.setAlignment(Pos.CENTER);
    pathBox.getChildren().addAll(pathLabel, pathValue);

    getChildren().addAll(clearButton, animationSpeedBox, startButton, pathBox);
    setAlignment(Pos.BOTTOM_CENTER);
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

    setPrefHeight(gridHeight * 0.5);
    setSpacing(getPrefHeight() * 0.125);

    clearButton.setDisable(animationStarted);

    animationSpeedBox.setDisable(animationStarted);
    animationSpeedBox.setSpacing(getPrefHeight() * 0.01);
    VBox buttonBox = (VBox) animationSpeedBox.getChildren().get(1);
    buttonBox.setSpacing(getPrefHeight() * 0.005);

    startButton.setMode(animationStarted);
    
    String pathString = "";
    String pathColour = "";
    switch (pathLength) {
      case 0 -> {
        pathString = "N/A";
        pathColour = "black";
      }
      case -1 -> {
        pathString = "None";
        pathColour = "red";
      }
      case -2 -> {
        pathString = "...";
        pathColour = "orange";
      }
      default -> {
        pathString = Integer.toString(pathLength);
        pathColour = "dodgerblue";
      }
    }
    pathValue.setText(pathString);
    pathValue.setStyle("-fx-text-fill: " + pathColour);
  }
}
