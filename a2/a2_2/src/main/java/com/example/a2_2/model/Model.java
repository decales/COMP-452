package com.example.a2_2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.example.a2_2.view.EnvironmentTile.TileType;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Model {

  private ArrayList<PublishSubscribe> subscribers;
  private Random random;
  private TileType[][] environmentGrid;
  private HashMap<GridPosition, Ant> antPositionMap;
  private double screenWidth, screenHeight;
  private Timeline animationTimer;

  public Model(double screenWidth, double screenHeight, int gridDimensions, int numAnts) {

    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    subscribers = new ArrayList<>();
    random = new Random();

    generateEnvironment(gridDimensions);
    spawnAnts(numAnts);
  }


  private void animate() {
    animationTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> moveAnts()));
    animationTimer.setRate(0.5);
    animationTimer.setCycleCount(Animation.INDEFINITE);
    animationTimer.play();
  }

  private void moveAnts() {
    
  }

  private boolean moveAnt(Ant ant, GridPosition newPosition) {
    // Cannot move to new position if it is outside of grid boundaries or another ant is already there
    if (newPosition.y < 0 || newPosition.x >= environmentGrid.length ||
        newPosition.x < 0 || newPosition.x >= environmentGrid.length ||
        antPositionMap.containsKey(newPosition)
        ) return false;

    antPositionMap.remove(ant.position); // Remove key at ant's previous position to show an ant is no longer positioned there
    antPositionMap.put(newPosition, ant); // Add a new key-value pair for ant and its new position
    return true;
  }

  private void generateEnvironment(int gridDimensions) {
    
    // Initialize grid and set all tiles to default terrain
    environmentGrid = new TileType[gridDimensions][gridDimensions];
    for (int i = 0; i < gridDimensions; i++) Arrays.fill(environmentGrid[i], TileType.Default);
    
    // Set four corners of environment grid to home tiles
    environmentGrid[0][0] = TileType.Home;
    environmentGrid[0][gridDimensions - 1] = TileType.Home;
    environmentGrid[gridDimensions - 1][0] = TileType.Home;
    environmentGrid[gridDimensions - 1][gridDimensions - 1] = TileType.Home;

    // Generate the rest of the tile types at varying ratios
    generateTileType(TileType.Water, 0.025);
    generateTileType(TileType.Poison, 0.025);
    generateTileType(TileType.Food, 0.01);
  }
  
  private void generateTileType(TileType tileType, double tileRatio) {

    int numTiles = (int) (Math.pow(environmentGrid.length, 2) * tileRatio);
    while (numTiles > 0) {
      GridPosition tilePosition = new GridPosition(random.nextInt(environmentGrid.length - 2) + 1, random.nextInt(environmentGrid.length - 2) + 1);

      if (environmentGrid[tilePosition.y][tilePosition.x] == TileType.Default) { // Only replace default terrain
        environmentGrid[tilePosition.y][tilePosition.x] = tileType;
        numTiles--;
      }
    }
  }

  private void spawnAnts(int numAnts) {
    antPositionMap = new HashMap<>();

    while (numAnts > 0) {
      GridPosition startPosition = new GridPosition(random.nextInt(environmentGrid.length - 1), random.nextInt(environmentGrid.length - 1));

      // Only place an ant on default terrain that does not already contain another ant
      if (environmentGrid[startPosition.y][startPosition.x] == TileType.Default && !antPositionMap.containsKey(startPosition)) {
        antPositionMap.put(startPosition, new Ant(startPosition));
        numAnts--;
      }
    }
  }

  public void addSubscribers(PublishSubscribe... subscribers) {
    this.subscribers.addAll(List.of(subscribers));
    updateSubscribers();
  }

  public void updateSubscribers() {
    for (PublishSubscribe subscriber : subscribers) {
      subscriber.update(screenHeight, environmentGrid, antPositionMap);
    }
  }
}
