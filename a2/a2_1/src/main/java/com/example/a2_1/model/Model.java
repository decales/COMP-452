package com.example.a2_1.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {
  
  protected ArrayList<PublishSubscribe> subscribers;
  protected double rootHeight;
  protected int[][] terrainGrid;
  protected int[][] entityGrid;

  public Model(double rootHeight) {
    
    subscribers = new ArrayList<>();

    this.rootHeight = rootHeight;

    // terrainGrid represents the type of terrain at each tile
    // At initialization, each tile is default terrain of value 1
    terrainGrid = new int[16][16];
    for (int i = 0; i < terrainGrid.length; i++) Arrays.fill(terrainGrid[i], 1);
    
    // entityGrid represents represents where the start, goal, and obstacles are in the grid
    // 0 -> empty tile
    // 1 -> start/character tile
    // 2 -> goal tile
    // 3 -> obstacle tile
    // At initialization, start tile is bottom left and goal tile is top right
    entityGrid = new int[16][16];
    entityGrid[entityGrid.length - 1][0] = 1;
    entityGrid[0][entityGrid.length - 1] = 2;
  }

  private void updateSubcribers() {
    subscribers.forEach(subcriber -> subcriber.update(rootHeight, terrainGrid, entityGrid));
  }

  public void addSubscribers(PublishSubscribe... subscribers) {
    this.subscribers.addAll(List.of(subscribers));
    updateSubcribers();
  }
}
