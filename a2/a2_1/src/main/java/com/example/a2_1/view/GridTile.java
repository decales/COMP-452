package com.example.a2_1.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GridTile extends ImageView {
  
  public enum TileType { Terrain, Grassland, Swamp, Character, Obstacle, Goal };

  public GridTile(TileType tileType, double spriteSize) {

    String spriteImage = "";
    switch(tileType) {
      case Terrain -> spriteImage = "terrain.png";
      case Grassland -> spriteImage = "grassland.png";
      case Swamp -> spriteImage = "swamp.png";
      case Character -> spriteImage = "black-ant.png";
      case Obstacle -> spriteImage = "red-ant.png";
      case Goal -> spriteImage = "blueberry.png";
    }
    setImage(new Image(spriteImage));
    setFitWidth(spriteSize);
    setFitHeight(spriteSize);
  }
} 

  

