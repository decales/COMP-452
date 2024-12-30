package com.example.a2_2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.example.a2_2.model.Ant.AntState;
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
  private ArrayList<GridPosition> homePositions;
  private double windowSize;
  private Timeline animationTimer;

  public Model(double windowSize, int gridDimension, int numAnts) {

    this.windowSize = windowSize;

    subscribers = new ArrayList<>();
    random = new Random();

    environmentGrid = new TileType[gridDimension][gridDimension];
    antPositionMap = new HashMap<>();
    homePositions = new ArrayList<>();
    generateEnvironment(numAnts);
  }

  public void animate() {
    // Move the ants and update the screen every second
    animationTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> moveAnts()));
    animationTimer.setCycleCount(Animation.INDEFINITE);
    animationTimer.play();
  }

  private void moveAnts() {
    for (int i = 0; i < antPositionMap.values().size(); i++) {
      Ant ant = (Ant) antPositionMap.values().toArray()[i];

      moveAnt(ant);

      // State machine that determines an ant's behaviour based on its state and the tile it traversed to
      // Note that isPaused is simply used to keep an ant in place for one frame after it visits a tile
      // On the next frame, the ant remains on the tile, and the action of the tile will trigger
      // This adds visual clarity when an ant has died or changed state
      switch(environmentGrid[ant.position.y][ant.position.x]) {
        case Default -> {}

        // Ant stepped on home
        case Home -> { 
          if (ant.state == AntState.ReturningHome && !ant.isPaused) {
            ant.isPaused = true;
          }
          else if (ant.isPaused) {
            ant.isPaused = false;
            generateAnts(1); // Spawn a new ant
            generateTileType(TileType.Food, 2); // Spawn food to account for the new ant 
            ant.state = AntState.SearchingWater; // Ant is now thirsty
          }
        }
        // Ant stepped on water
        case Water -> { 
          if (ant.state == AntState.SearchingWater && !ant.isPaused) {
            ant.isPaused = true;
          }
          else if (ant.isPaused) {
            ant.isPaused = false;
            ant.state = AntState.SearchingFood; // Ant is now hungry
          }
        }
        // Ant stepped on poison
        case Poison -> {
          if (ant.isPaused) {
            antPositionMap.remove(ant.position); // Kill the ant :(
            ant = null;
          }
          else {
            ant.isPaused = true;
          }
        }
        // Ant stepped on food
        case Food -> { 
          if (ant.state == AntState.SearchingFood && !ant.isPaused) {
            ant.isPaused = true;
          }
          else if (ant.isPaused) {
            ant.isPaused = false;
            environmentGrid[ant.position.y][ant.position.x] = TileType.Default; // Remove the food from the grid
            generateTileType(TileType.Food, 1); // Spawn another food to account for the one that was just picked up
            ant.state = AntState.ReturningHome; // Ant is now returning home with food
          }
        }
      }
    }
    updateSubscribers();
  }

  private void moveAnt(Ant ant) {
    GridPosition nextPosition = ant.getNextPosition(homePositions);

    // Check if an ant's next move is valid
    if (nextPosition.y >= 0 && nextPosition.y < environmentGrid.length &&
        nextPosition.x >= 0 && nextPosition.x < environmentGrid.length &&
        !antPositionMap.containsKey(nextPosition) 
    ) {
      // If the move is valid, update the antPosition map to reflect the ant's new position
      antPositionMap.remove(ant.position, ant); // Remove key at ant's previous position to show an ant is no longer positioned there
      antPositionMap.put(nextPosition, ant); // Add a new key-value pair for ant and its new position
      ant.position = nextPosition;
    }
  }

  private void generateEnvironment(int numAnts) {
    // Initialize grid and set all tiles to default terrain
    for (int i = 0; i < environmentGrid.length; i++) Arrays.fill(environmentGrid[i], TileType.Default);
    
    // Generate the rest of the tile types at varying ratios
    generateTileType(TileType.Home, (int) (numAnts * 0.5));
    generateTileType(TileType.Water, (int) (Math.pow(environmentGrid.length, 2) * 0.025));
    generateTileType(TileType.Poison, (int) (Math.pow(environmentGrid.length, 2) * 0.025));
    generateTileType(TileType.Food, (int) (numAnts * 2));
    
    // Place the starting amount of ants on the grid
    generateAnts(numAnts);
  }

  private void generateTileType(TileType tileType, int numTiles) {
    while (numTiles > 0) {
      GridPosition tilePosition = new GridPosition(random.nextInt(environmentGrid.length), random.nextInt(environmentGrid.length));

      if (environmentGrid[tilePosition.y][tilePosition.x] == TileType.Default) { // Only replace default terrain
        environmentGrid[tilePosition.y][tilePosition.x] = tileType;
        if (tileType == TileType.Home) homePositions.add(tilePosition); // Keep track of home tiles
        numTiles--;
      }
    }
  }

  private void generateAnts(int numAnts) {
    while (numAnts > 0) {
      GridPosition startPosition = new GridPosition(random.nextInt(environmentGrid.length), random.nextInt(environmentGrid.length));

      // Only place an ant on default terrain that does not already contain another ant
      if (environmentGrid[startPosition.y][startPosition.x] == TileType.Default && !antPositionMap.containsKey(startPosition)) {
        antPositionMap.put(startPosition, new Ant(startPosition));
        numAnts--;
      }
    }
  }

  public void updateWindowSize(double windowSize) {
    this.windowSize = windowSize;
    updateSubscribers();
  }

  public void addSubscribers(PublishSubscribe... subscribers) {
    this.subscribers.addAll(List.of(subscribers));
    updateSubscribers();
  }

  public void updateSubscribers() {
    for (PublishSubscribe subscriber : subscribers) {
      subscriber.update(windowSize, environmentGrid, antPositionMap);
    }
  }
}
