package com.example.a2_2.model;

import java.util.Random;

public class Ant {

  public enum AntState { SearchingFood, ReturningHome, SearchingWater }

  public GridPosition position;
  public AntState state;
  
  private Random random;
  
  public Ant(GridPosition position) {

    this.position = position;
    state = AntState.SearchingFood;
    
    random = new Random();
  }

  public void move() {
    switch(state) {
      case SearchingFood, SearchingWater -> roam();
      case ReturningHome -> path();
    }
  }

  public GridPosition roam() {
    // Choose a random direction to traverse
    int [][] traversalDirections = { {0,1}, {0,-1}, {1,0}, {1,1}, {1,-1}, {-1,0}, {-1,1}, {-1,-1} };
    int[] randomDirection = traversalDirections[random.nextInt(traversalDirections.length)];
    return new GridPosition(position.y + randomDirection[0], position.x + randomDirection[1]);
  }

  private void path() {

  }
}
