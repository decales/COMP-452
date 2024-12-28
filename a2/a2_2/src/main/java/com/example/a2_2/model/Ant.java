package com.example.a2_2.model;

import java.util.Random;

public class Ant {

  public enum AntState { SearchingFood, ReturningHome, SearchingWater }

  public int posY, posX;
  public AntState state;
  
  private Random random;
  
  public Ant() {

    random = new Random();
    state = AntState.SearchingFood; // Ants

  }

  public void roam() {

    // Choose random direction
    // Choose random number of blocks to travel
    // Choose random number of frames to delay before moving again

  }

  public void returnHome() {

  }
}
