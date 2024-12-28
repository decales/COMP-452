package com.example.a2_2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Model {

  private ArrayList<PublishSubscribe> subscribers;
  private Random random;
  private int[][] environmentGrid;
  private Ant[][] antGrid;
  private double screenWidth, screenHeight;

  public Model(double screenWidth, double screenHeight, int gridDimensions, int numAnts) {

    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    subscribers = new ArrayList<>();
    random = new Random();

    generateEnvironment(gridDimensions);
    spawnAnts(numAnts);
  }


  private void generateEnvironment(int gridDimensions) {
    // 0 -> empty
    // 1 -> home
    // 2 -> water
    // 3 -> poison
    // 4 -> food
    
    environmentGrid = new int[gridDimensions][gridDimensions];

    HashMap<Integer, Integer> cellRatioMap = new HashMap<>();
    cellRatioMap.put(2, (int) (Math.pow(gridDimensions, 2) * 0.025)); // 5% of environment should be water
    cellRatioMap.put(3, (int) (Math.pow(gridDimensions, 2) * 0.025)); // 5% of environment should be poison
    cellRatioMap.put(4, 5); // two pieces of food should spawn for every initial ants

    for (Integer cellTypeValue : cellRatioMap.keySet()) {
      
      int numCells = cellRatioMap.get(cellTypeValue);
      while (numCells > 0) {
        
        int cellY = random.nextInt(gridDimensions - 2) + 1;
        int cellX = random.nextInt(gridDimensions - 2) + 1;

        if (environmentGrid[cellY][cellX] == 0) { // Only replace empty terrain
          environmentGrid[cellY][cellX] = cellTypeValue;
          numCells--;
        }
      }
    }
  }

  private void spawnAnts(int numAnts) {
    antGrid = new Ant[environmentGrid.length][environmentGrid.length];
    
    int _numAnts = numAnts;
    while (_numAnts > 0) {
      
      int antStartY = random.nextInt(environmentGrid.length - 1);
      int antStartX = random.nextInt(environmentGrid.length - 1);

      if (environmentGrid[antStartY][antStartX] == 0 && antGrid[antStartY][antStartX] == null) { // Only spawn ant on empty terrain where no other ants are
        antGrid[antStartY][antStartX] = new Ant();
        _numAnts--;
      }
    }
  }

  public void addSubscribers(PublishSubscribe... subscribers) {
    this.subscribers.addAll(List.of(subscribers));
    updateSubscriber();
  }

  public void updateSubscriber() {
    for (PublishSubscribe subscriber : subscribers) {
      subscriber.update(screenHeight, environmentGrid, antGrid);
    }
  }
}
