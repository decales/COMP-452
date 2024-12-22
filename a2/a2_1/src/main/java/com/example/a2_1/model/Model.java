package com.example.a2_1.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.a2_1.view.TileSelector.TileSelectorType;

public class Model {
  
  private ArrayList<PublishSubscribe> subscribers;
  private double rootHeight;
  private int[][] terrainGrid;
  private int[][] entityGrid;
  private int character_i, character_j;
  private int goal_i, goal_j;
  private TileSelectorType currentSelectorType;
  
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
    updateCharacterPos(0, entityGrid.length - 1);
    updateGoalPos(entityGrid.length - 1, 0);

    currentSelectorType = TileSelectorType.Terrain;
  }

  public void updateSelectorType(TileSelectorType type) {
    currentSelectorType = type;
    updateSubcribers();
  }

  public void updateGrid(int i, int j) {

    switch(currentSelectorType) {
      case Terrain -> terrainGrid[i][j] = 1;
      case Grassland -> terrainGrid[i][j] = 3;
      case Swamp -> terrainGrid[i][j] = 4;
      case Character -> updateCharacterPos(i, j);
      case Goal -> updateGoalPos(i, j);
      case Obstacle -> updateObstacle(i, j);
    }
    updateSubcribers();
  }
  
  private boolean updateCharacterPos(int i, int j) {
    if (entityGrid[i][j] == 2) return false; // Do not allow placing character on goal
    entityGrid[character_i][character_j] = 0;
    entityGrid[i][j] = 1;
    character_i = i;
    character_j = j;
    return true;
  }

  private boolean updateGoalPos(int i, int j) {
    if (entityGrid[i][j] == 1) return false; // Do not allow placing goal on character 
    entityGrid[goal_i][goal_j] = 0;
    entityGrid[i][j] = 2;
    goal_i = i;
    goal_j = j;
    return true;
  }

  private boolean updateObstacle(int i, int j) {
    int currentEntity = entityGrid[i][j];
    if (currentEntity == 1 || currentEntity == 2) return false; // Do not allow placing obstacle on character or goal
    if (currentEntity == 3) entityGrid[i][j] = 0; // Delete obstacle when selecting tile that already has one
    else entityGrid[i][j] = 3; // Place obstacle on empty tile
    return true;
  }

  public void addSubscribers(PublishSubscribe... subscribers) {
    this.subscribers.addAll(List.of(subscribers));
    updateSubcribers();
  }

  private void updateSubcribers() {
    subscribers.forEach(subcriber -> subcriber.update(rootHeight, terrainGrid, entityGrid, currentSelectorType));
  }
}
