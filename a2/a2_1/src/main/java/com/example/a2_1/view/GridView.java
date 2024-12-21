package com.example.a2_1.view;

import com.example.a2_1.model.PublishSubscribe;
import com.example.a2_1.view.GridTile.TileType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GridView extends GridPane implements PublishSubscribe {

  private double tileSpacing;

  public GridView(double tileSpacing) {
    this.tileSpacing = tileSpacing;
    setHgap(this.tileSpacing);
    setVgap(this.tileSpacing);
    setStyle("-fx-background-color: darkgrey; -fx-border-color: darkgrey; -fx-border-width:" + tileSpacing);
  }

  @Override
  public void update(double gridHeight, int[][] terrainGrid, int[][] entityGrid) {

    int tileCount = terrainGrid.length;
    for (int i = 0; i < tileCount; i++) {
      for (int j = 0; j < tileCount; j++) {

        StackPane tile = new StackPane();
        TileType tileType = null;
        double tileSize = gridHeight / tileCount - tileSpacing;

        // Determine tile terrain type
        switch(terrainGrid[i][j]) {
          case 1 -> tileType = TileType.Terrain;
          case 3 -> tileType = TileType.Grassland;
          case 4 -> tileType = TileType.Swamp;
        }
        tile.getChildren().add(new GridTile(tileType, tileSize));

        // Determine if tile contains an entity
        switch(entityGrid[i][j]) {
          case 1 -> tileType = TileType.Character;
          case 2 -> tileType = TileType.Goal;
          case 3 -> tileType = TileType.Obstacle;
        }
        tile.getChildren().add(new GridTile(tileType, tileSize));

        add(tile, i, j);
      }
    }
  }
}

