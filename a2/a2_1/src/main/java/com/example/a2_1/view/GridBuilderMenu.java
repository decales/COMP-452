package com.example.a2_1.view;

import com.example.a2_1.model.PublishSubscribe;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GridBuilderMenu extends VBox implements PublishSubscribe {

  public GridBuilderMenu() {
    ImageView terrainButton = new ImageView();
    terrainButton.setImage(new Image("terrain.png"));
  }

  @Override
  public void update(double rootHeight, int[][] terrainGrid, int[][] entityGrid) {
    // TODO Auto-generated method stub  
  }
  
}
