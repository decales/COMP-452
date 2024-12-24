package com.example.a2_1.view;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.TileSelector.TileSelectorType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class TileMenu extends VBox implements PublishSubscribe {

  public TileMenu(Controller controller) {
    
    for (TileSelectorType type : TileSelectorType.values()) {
      TileSelector selector = new TileSelector(type);
      selector.setOnMouseClicked(controller::handleMouseClicked);
      getChildren().add(selector);
    } 
    
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
    
    setPrefHeight(gridHeight * 0.5);
    setSpacing(getPrefHeight() * 0.05);

    for (Node child : getChildren()) {
      TileSelector selector = (TileSelector) child;
      selector.setSize(gridHeight * 0.0667);
      selector.setEnabled(animationStarted);
      selector.setBorderColour(currentSelectorType.equals(selector.selectorType));
    }
  }
}
