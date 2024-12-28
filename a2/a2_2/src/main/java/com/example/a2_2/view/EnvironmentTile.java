package com.example.a2_2.view;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class EnvironmentTile extends StackPane {

  public enum TileType { Default, Home, Water, Poison, Food };
  
  public TileType type;
  public int y, x;
  private ImageView terrainSprite;
  private ImageView antSprite;

  public EnvironmentTile(TileType type, int y, int x) {
    
    this.type = type;
    this.y = y;
    this.x = x;

    terrainSprite = new ImageView(new Image(String.format("%s.png", type.toString().toLowerCase())));
    antSprite = new ImageView(new Image("ant.png"));
    getChildren().addAll(terrainSprite, antSprite);
  }

  public void updateTile(boolean containsAnt, double size) {
    antSprite.setVisible(containsAnt);
    for (ImageView image : List.of(terrainSprite, antSprite)) {
      image.setFitWidth(size);
      image.setFitHeight(size);
    }
  }
}
