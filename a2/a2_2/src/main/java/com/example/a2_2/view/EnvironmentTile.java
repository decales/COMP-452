package com.example.a2_2.view;

import java.util.List;

import com.example.a2_2.model.Ant;
import com.example.a2_2.model.GridPosition;
import com.example.a2_2.model.Ant.AntState;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class EnvironmentTile extends StackPane {

  public enum TileType { Default, Home, Water, Poison, Food };
  
  public TileType type;
  public GridPosition position;
  private ImageView terrainSprite;
  private ImageView antSprite;

  public EnvironmentTile(TileType type, GridPosition position) {

    this.position = position;

    terrainSprite = new ImageView();
    setTerrainSprite(type);

    antSprite = new ImageView("searchingfood.png");
    getChildren().addAll(terrainSprite, antSprite);
  }

  public void setTerrainSprite(TileType type) {
    // Technically only used to update food terrain after an ant has picked up food
    if (type != this.type) {
      terrainSprite.setImage(new Image(String.format("%s.png", type.toString().toLowerCase())));
      this.type = type;
    }
  }

  public void setAntSprite(Ant ant) {
    // Display an ant on the tile if there should be one, and set the ant's colour based on its state
    boolean containsAnt = ant != null;
    antSprite.setVisible(containsAnt);

    if (containsAnt) {
      antSprite.setImage(new Image(String.format("%s.png", ant.state.toString().toLowerCase())));
      antSprite.setRotate(ant.rotationAngle);
    }
  }

  public void setSize(double size) {
    for (ImageView image : List.of(terrainSprite, antSprite)) {
      image.setFitWidth(size);
      image.setFitHeight(size);
    }
  }
}
