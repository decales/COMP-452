package com.example.a2_1.view;

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GridTile extends StackPane {
  
  public enum TileType { Terrain, Grassland, Swamp, Character, Obstacle, Goal, None};

  public boolean isVisited;
  public int i, j;

  public GridTile(int i, int j, TileType terrainType, TileType entityType, int visitType, double spriteSize) {

    this.i = i;
    this.j = j;

    double borderWidth = spriteSize * 0.075;

    double spriteOrientation = 0;
    
    for (TileType tileType : List.of(terrainType, entityType)) {
      String spriteSource = "";
      switch(tileType) {
        case None -> { continue;}
        case Terrain -> spriteSource = "terrain.png";
        case Grassland -> spriteSource = "grassland.png";
        case Swamp -> spriteSource = "swamp.png";
        case Character -> spriteSource = "black-ant.png";
        case Goal -> spriteSource = "blueberry.png";
        case Obstacle -> spriteSource = "red-ant.png";
      }
      ImageView sprite = new ImageView(new Image(spriteSource));
      sprite.setRotate(spriteOrientation);
      sprite.setFitWidth(spriteSize - borderWidth);
      sprite.setFitHeight(spriteSize - borderWidth);
      getChildren().add(sprite);
    }

    String color = "";
    switch(visitType) {
      case 0 -> color = "darkgrey";
      case 1 -> color = "red";
      case 2 -> color = "blue";
    }

    setStyle(String.format("-fx-border-color: %s; -fx-border-width: %f", color,  borderWidth));
  }
} 

  

