package com.example.a2_2.view;

import java.util.List;

import com.example.a2_2.model.Ant;
import com.example.a2_2.model.GridPosition;

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
    
    this.type = type;
    this.position = position;

    terrainSprite = new ImageView(new Image(String.format("%s.png", type.toString().toLowerCase())));
    antSprite = new ImageView(new Image("ant.png"));
    getChildren().addAll(terrainSprite, antSprite);
  }

  public void updateTile(Ant antAtTile, double size) {

    boolean antExists = antAtTile != null;

    antSprite.setVisible(antExists);

    if (antExists) {
      // Change ant sprite based on ant state
      // Change angle of ant sprite based on the direction it traversed
    }

    for (ImageView image : List.of(terrainSprite, antSprite)) {
      image.setFitWidth(size);
      image.setFitHeight(size);
    }
  }
}
