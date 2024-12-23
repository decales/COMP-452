package com.example.a2_1.view;

import com.example.a2_1.Controller;
import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.GridTile.TileType;
import com.example.a2_1.view.TileSelector.TileSelectorType;
import javafx.scene.layout.GridPane;

public class GridView extends GridPane implements PublishSubscribe {

  private double tileSpacing;
  private Controller controller;

  public GridView(Controller controller, double tileSpacing) {
    this.controller = controller;
    this.tileSpacing = tileSpacing;
    setHgap(this.tileSpacing);
    setVgap(this.tileSpacing);
  }

  @Override
  public void update(
      double gridHeight,
      int[][] terrainGrid,
      int[][] entityGrid,
      int[][] visited,
      TileSelectorType currentSelectorType) {

    getChildren().clear();

    int tileCount = terrainGrid.length;
    for (int i = 0; i < tileCount; i++) {
      for (int j = 0; j < tileCount; j++) {

        // Determine tile terrain type
        TileType terrainType = null;
        switch(terrainGrid[i][j]) {
          case 1 -> terrainType = TileType.Terrain;
          case 3 -> terrainType = TileType.Grassland;
          case 4 -> terrainType = TileType.Swamp;
        } 

        // Determine if tile contains an entity
        TileType entityType = null;
        switch(entityGrid[i][j]) {
          case 0 -> entityType = TileType.None;
          case 1 -> entityType = TileType.Character;
          case 2 -> entityType = TileType.Goal;
          case 3 -> entityType = TileType.Obstacle;
        }
        
        GridTile tile = new GridTile(i, j, terrainType, entityType, visited[i][j], gridHeight / tileCount - tileSpacing);
        tile.setOnMouseClicked(controller::handleMouseClicked); 
        add(tile, i, j);
      }
    }
  }
}

