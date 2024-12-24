package com.example.a2_1.view;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.GridTile.TileType;
import com.example.a2_1.view.TileSelector.TileSelectorType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridView extends GridPane implements PublishSubscribe {

  private double tileSpacing;
  private int gridDimension;

  public GridView(Controller controller, double tileSpacing, int gridDimension) {

    this.tileSpacing = tileSpacing;
    this.gridDimension = gridDimension;

    setHgap(this.tileSpacing);
    setVgap(this.tileSpacing);
    
    for (int i = 0; i < gridDimension; i++) {
      for (int j = 0; j < gridDimension; j++) {
        GridTile tile = new GridTile(i, j);
        tile.setOnMouseClicked(controller::handleMouseClicked);
        add(tile, i, j);
      }
    }
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

    for (Node child : getChildren()) {
      GridTile tile = (GridTile) child;

      tile.setSize(gridHeight / gridDimension - tileSpacing);
      tile.setBorderColour(visited[tile.i][tile.j]);
      tile.setDisable(animationStarted); // Cannot modify tiles when animation is running;

      if (!animationStarted) { // Only set terrain when animation is not running
        TileType terrainType = null;
        switch(terrainGrid[tile.i][tile.j]) {
          case 1 -> terrainType = TileType.Terrain;
          case 3 -> terrainType = TileType.Grassland;
          case 4 -> terrainType = TileType.Swamp;
        } 
        tile.setTerrainSprite(terrainType);
      }

      TileType entityType = null;
      switch(entityGrid[tile.i][tile.j]) {
        case 1 -> entityType = TileType.Character;
        case 2 -> entityType = TileType.Goal;
        case 3 -> entityType = TileType.Obstacle;
      }
      tile.setEntitySprite(entityType);
    }
  }
}

