package com.example.a2_1.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class TileSelector extends StackPane {

  public enum TileSelectorType { Terrain, Grassland, Swamp, Character, Obstacle, Goal };

  protected TileSelectorType selectorType;
  private boolean isSelected;

  public TileSelector(TileSelectorType selectorType, double spriteSize) {
    
    this.selectorType = selectorType;

    String spriteImage = "";
    switch(this.selectorType) {
      case Terrain -> spriteImage = "terrain.png";
      case Grassland -> spriteImage = "grassland.png";
      case Swamp -> spriteImage = "swamp.png";
      case Character -> spriteImage = "black-ant.png";
      case Obstacle -> spriteImage = "red-ant.png";
      case Goal -> spriteImage = "blueberry.png";
    }

    ImageView sprite = new ImageView(new Image(spriteImage));
    sprite.setFitWidth(spriteSize);
    sprite.setFitHeight(spriteSize);
    sprite.preserveRatioProperty().set(true);
    getChildren().add(sprite);
    
    setStyle(String.format("-fx-border-color: %s; -fx-border-width: %f", (isSelected) ? "lime" : "darkgrey", spriteSize * 0.075));
  }
}
