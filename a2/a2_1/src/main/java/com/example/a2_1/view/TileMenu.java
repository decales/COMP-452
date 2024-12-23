package com.example.a2_1.view;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.TileSelector.TileSelectorType;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class TileMenu extends VBox implements PublishSubscribe {

  private Controller controller;

  public TileMenu(Controller controller) {
    
    this.controller = controller;
    setAlignment(Pos.CENTER);
  }

  @Override
  public void update(
      double gridHeight,
      int[][] terrainGrid,
      int[][] entityGrid,
      int[][] visited,
      TileSelectorType currentSelectorType,
      boolean animationStarted,
      int pathLength) {

    getChildren().clear();
    
    setPrefHeight(gridHeight * 0.5);
    setSpacing(getPrefHeight() * 0.05);
  
    // Draw tile selector buttons
    for (TileSelectorType type : TileSelectorType.values()) {
      TileSelector selector = new TileSelector(type, type.equals(currentSelectorType), animationStarted, gridHeight * 0.0667);
      if (!animationStarted) selector.setOnMouseClicked(controller::handleMouseClicked); // Tiles only clickable when animation not started
      getChildren().add(selector);
    } 
  }
}
