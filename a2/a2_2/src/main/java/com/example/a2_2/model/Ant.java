package com.example.a2_2.model;

import java.util.Random;

public class Ant {

  public enum AntState { SearchingFood, ReturningHome, SearchingWater }

  public GridPosition position;
  public AntState state;
  private int[][] traversalDirections;
  
  private Random random;
  
  public Ant(GridPosition position) {

    this.position = position;
    state = AntState.SearchingFood;
    
    traversalDirections = new int[][] { {0,0}, {0,1}, {0,-1}, {1,0}, {1,1}, {1,-1}, {-1,0}, {-1,1}, {-1,-1} };
    random = new Random();
  }

  public void move() {
    switch(state) {
      case SearchingFood, SearchingWater -> roam();
      case ReturningHome -> path();
    }
  }

  private void roam() {
    // Choose a random direction to traverse
    int[] randomDirection = traversalDirections[random.nextInt(traversalDirections.length - 1)];

  }

  private void path() {

  }
}
