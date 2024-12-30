package com.example.a2_2.view;

import java.util.HashMap;
import com.example.a2_2.model.Ant;
import com.example.a2_2.model.GridPosition;
import com.example.a2_2.model.PublishSubscribe;
import com.example.a2_2.view.EnvironmentTile.TileType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class EnvironmentGrid extends GridPane implements PublishSubscribe {

  private void initGrid(TileType[][] environmentGrid, double borderSize) {

    getChildren().clear();
    setStyle("-fx-background-color: #2e4c2e; -fx-border-color: darkslategrey; -fx-border-width: " + borderSize);

    int gridDimension = environmentGrid.length;
    for (int i = 0; i < gridDimension; i++) {
      for (int j = 0; j < gridDimension; j++) {
        add(new EnvironmentTile(environmentGrid[i][j], new GridPosition(j, i)), i, j);
      }
    }
  }

  public void update(double windowSize, TileType[][] environmentGrid, HashMap<GridPosition, Ant> antPositionMap) {

    double tileSize = windowSize / environmentGrid.length;
    double tileGap = tileSize *0.1;
    setHgap(tileGap);
    setVgap(tileGap);
    
    // Initialize GridPane children only when the grid dimenions change
    if (Math.pow(environmentGrid.length, 2) != getChildren().size()) initGrid(environmentGrid, tileGap);

    // Update the GridPane children
    for (Node node : getChildren()) {
      EnvironmentTile tile = (EnvironmentTile) node;

      tile.setTerrainSprite(environmentGrid[tile.position.y][tile.position.x]);
      tile.setAntSprite(antPositionMap.get(new GridPosition(tile.position.y, tile.position.x)));
      tile.setSize(tileSize);
    }
  }
}
