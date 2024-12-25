package com.example.a2_1.view;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GridTile extends StackPane {
  
  public enum TileType { Terrain, Grassland, Swamp, Character, Obstacle, Goal };

  public int i, j;
  private ImageView terrainSprite;
  private ImageView entitySprite;
  private double spriteSize;

  public GridTile(int i, int j) {
    this.i = i;
    this.j = j;

    terrainSprite = new ImageView();
    entitySprite = new ImageView();
    getChildren().addAll(terrainSprite, entitySprite);
  }

  public void setSize(double spriteSize) {
    this.spriteSize = spriteSize;
    for (ImageView sprite : List.of(terrainSprite, entitySprite)) {
      sprite.setFitWidth(spriteSize - spriteSize * 0.075);
      sprite.setFitHeight(spriteSize - spriteSize * 0.075);
    }
  }

  public void setTerrainSprite(TileType terrainType) {
    terrainSprite.setImage(new Image(getSpriteSource(terrainType)));
  }
  
  public void setEntitySprite(TileType entityType) {
    String entitySource = getSpriteSource(entityType);
    entitySprite.setImage( (entitySource.isEmpty()) ? null : new Image(entitySource));
  }

  private String getSpriteSource(TileType tileType) {
      String spriteSource = "";
      switch(tileType) {
        case Terrain -> spriteSource = "terrain.png";
        case Grassland -> spriteSource = "grassland.png";
        case Swamp -> spriteSource = "swamp.png";
        case Character -> spriteSource = "black-ant.png";
        case Goal -> spriteSource = "blueberry.png";
        case Obstacle -> spriteSource = "red-ant.png";
        case null -> {}
      }
      return spriteSource;
  }

  public void setBorderColour(int visitType) {
    String color = "";
    switch(visitType) {
      case 0, 1 -> color = "darkgrey";
      case 2 -> color = "orange";
      case 3 -> color = "dodgerblue";
    }
    setStyle(String.format("-fx-border-color: %s; -fx-border-width: %f", color,  spriteSize * 0.075));
  }
} 

  

