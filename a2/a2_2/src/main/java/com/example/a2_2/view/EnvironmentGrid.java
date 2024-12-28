package com.example.a2_2.view;

import java.util.HashMap;
import com.example.a2_2.model.Ant;
import com.example.a2_2.model.GridPosition;
import com.example.a2_2.model.PublishSubscribe;
import com.example.a2_2.view.EnvironmentTile.TileType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class EnvironmentGrid extends GridPane implements PublishSubscribe {

  private void initGrid(TileType[][] environmentGrid) {

    int gridDimension = environmentGrid.length;
    for (int i = 0; i < gridDimension; i++) {
      for (int j = 0; j < gridDimension; j++) {
        add(new EnvironmentTile(environmentGrid[i][j], new GridPosition(i, j)), i, j);
      }
    }
  }

  public void update(double size, TileType[][] environmentGrid, HashMap<GridPosition, Ant> antPositionMap) {

    if (getChildren().isEmpty()) initGrid(environmentGrid);

    for (Node node : getChildren()) {
      EnvironmentTile tile = (EnvironmentTile) node;

      Ant antAtTile = antPositionMap.get(new GridPosition(tile.position.y, tile.position.x)); // Can be null if no ant exists
      tile.updateTile(antAtTile, size / environmentGrid.length);
    }
  }
}
