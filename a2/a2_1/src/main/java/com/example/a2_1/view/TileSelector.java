package com.example.a2_1.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class TileSelector extends StackPane {

  public enum TileSelectorType { Terrain, Grassland, Swamp, Obstacle, Character, Goal };

  public TileSelectorType selectorType;

  public TileSelector(TileSelectorType selectorType, boolean isSelected, boolean animationStarted, double spriteSize) {
    
    this.selectorType = selectorType;

    String spriteSource = "";
    switch(this.selectorType) {
      case Terrain -> spriteSource = "terrain.png";
      case Grassland -> spriteSource = "grassland.png";
      case Swamp -> spriteSource = "swamp.png";
      case Obstacle -> spriteSource = "red-ant.png";
      case Character -> spriteSource = "black-ant.png";
      case Goal -> spriteSource = "blueberry.png";
    }

    ImageView sprite = new ImageView(new Image(spriteSource));
    double borderWidth = spriteSize * 0.075;
    sprite.setFitWidth(spriteSize - borderWidth);
    sprite.setFitHeight(spriteSize - borderWidth);
    getChildren().add(sprite);

    // 'Grey-out' button when animation is started to show it is not clickable
    if (animationStarted) {
      ColorAdjust greyScaleFilter = new ColorAdjust();
      greyScaleFilter.setSaturation(-1);
      sprite.setEffect(greyScaleFilter);
    }
    
    setStyle(String.format(
          "-fx-border-color: %s;" +
          "-fx-border-width: %f;" +
          "-fx-background-color: darkgrey",
          (isSelected && !animationStarted) ? "magenta" : "grey", borderWidth));
  }
}
