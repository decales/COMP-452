package com.example.a2_2.view;

import com.example.a2_2.model.Ant;
import com.example.a2_2.model.PublishSubscribe;
import com.example.a2_2.view.EnvironmentTile.TileType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class EnvironmentGrid extends GridPane implements PublishSubscribe {

  private void initGrid(int[][] environmentGrid) {

    int gridDimension = environmentGrid.length;
    for (int i = 0; i < gridDimension; i++) {
      for (int j = 0; j < gridDimension; j++) {

        TileType type = null;
        switch(environmentGrid[i][j]) {
          case 0 -> type = TileType.Default;
          case 1 -> type = TileType.Home;
          case 2 -> type = TileType.Water;
          case 3 -> type = TileType.Poison;
          case 4 -> type = TileType.Food;
        }
        add(new EnvironmentTile(type, i, j), i, j);
      }
    }
  }

  public void update(double size, int[][] environmentGrid, Ant[][] antGrid) {

    if (getChildren().isEmpty()) initGrid(environmentGrid);

    for (Node node : getChildren()) {
      EnvironmentTile tile = (EnvironmentTile) node;
      boolean tileContainsAnt = antGrid[tile.y][tile.x] != null;
      tile.updateTile(tileContainsAnt, size / environmentGrid.length);
    }
  }
}
