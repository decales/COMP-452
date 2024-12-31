package com.example.a2_2.view;

import java.util.HashMap;

import com.example.a2_2.model.Ant;
import com.example.a2_2.model.GridPosition;
import com.example.a2_2.model.PublishSubscribe;
import com.example.a2_2.view.EnvironmentTile.TileType;

import javafx.scene.layout.VBox;

public class ControlMenu extends VBox implements PublishSubscribe {


  public ControlMenu() {

  }
  
  @Override
public void update(double windowSize, boolean simulationStarted, TileType[][] environmentGrid,
        HashMap<GridPosition, Ant> antPositionMap) {
    // TODO Auto-generated method stub
    
}
}
