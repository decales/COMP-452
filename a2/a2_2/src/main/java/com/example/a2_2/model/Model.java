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
  private double windowSize;
  private Timeline animationTimer;
  private boolean animationPlaying;
  private boolean simulationStarted;

  private TileType[][] environmentGrid;
  private int numberAnts;
  private HashMap<GridPosition, Ant> antPositionMap;
  private ArrayList<GridPosition> homePositions;

  public Model(double windowSize) {

    this.windowSize = windowSize;
    subscribers = new ArrayList<>();
    random = new Random();

    // Move the ants and update the screen every second
    animationTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> moveAnts()));
    animationTimer.setCycleCount(Animation.INDEFINITE);

    antPositionMap = new HashMap<>(); // Maps a GridPosition in the environmentGrid to an Ant object, container for Ants
    homePositions = new ArrayList<>(); // Keep track of home tiles in environment grid separately for ant home pathing purposes

    initializeGrid(16);
  }

  public void startSimulation() {
    simulationStarted = true;
    generate(); // Generate tiles and ants
    playAnimation();
    updateSubscribers();
  }

  public void resetSimulation() {
    simulationStarted = false;
    pauseAnimation();
    
    // Reset structures related to environment
    initializeGrid(environmentGrid.length);
    antPositionMap.clear();
    homePositions.clear();
    
    updateSubscribers();
  }

  public void playAnimation() {
    animationPlaying = true;
    animationTimer.play();
    updateSubscribers();
  }

  public void pauseAnimation() {
    animationPlaying = false;
    animationTimer.pause();
    updateSubscribers();
  }

  public void setNumberAnts(int numberAnts) {
    this.numberAnts = numberAnts;
    updateSubscribers();
  }

  public void initializeGrid(int gridDimension) {
    // Initialize grid and set all tiles to default terrain
    environmentGrid = new TileType[gridDimension][gridDimension];
    for (int i = 0; i < environmentGrid.length; i++) Arrays.fill(environmentGrid[i], TileType.Default);
    updateSubscribers();
  }

  private void generate() {
    double totalTiles = Math.pow(environmentGrid.length, 2);

    // Only generate environment if less than 33.3% of the grid would be filled with Ants
    if (antPositionMap.size() + numberAnts < totalTiles * 0.333) { // 8:21, 16:85, 32:340

      // Generate the rest of the tile types at varying ratios
      generateTileType(TileType.Home, (int) Math.ceil(totalTiles * 0.005)); // 0.5% of grid
      generateTileType(TileType.Water, (int) Math.ceil(totalTiles * 0.025)); // 2.5% of grid
      generateTileType(TileType.Poison, (int) Math.ceil(totalTiles * 0.025)); // 2.5% of grid
      generateTileType(TileType.Food, (int) Math.ceil(numberAnts * 1.5)); // 1.5 * maxAnt% = 50% of grid (max)
      
      // Ants only spawn on default terrain
      // Default terrain = 100 - (0.5 + 2.5 + 2.5 + 50) = 44.5% remaining tiles
      generateAnts(numberAnts);
    }
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

  private void generateAnts(int numberAnts) {
    // Redundant check needed based on how this function is used to spawn a new ant when another ant brings food home
    if (antPositionMap.size() + numberAnts < Math.pow(environmentGrid.length, 2) * 0.333) {
      while (numberAnts > 0) {
        GridPosition startPosition = new GridPosition(random.nextInt(environmentGrid.length), random.nextInt(environmentGrid.length));

        // Only place an ant on default terrain that does not already contain another ant;
        if (environmentGrid[startPosition.y][startPosition.x] == TileType.Default && !antPositionMap.containsKey(startPosition)) {
          antPositionMap.put(startPosition, new Ant(startPosition));
          numberAnts--;
        }
      }
    }
  }

  private void moveAnts() {
    for (int i = 0; i < antPositionMap.values().size(); i++) {

      Ant ant = (Ant) antPositionMap.values().toArray()[i];
      moveAnt(ant); // See this function and Ant.java to see how ant movement is determined  

      // State machine that determines an ant's behaviour based on its state and the tile it traversed to
      // Note that isPaused is simply used to keep an ant in place for one frame after it visits a tile
      // On the next frame, the ant remains on the tile, and the action of the tile will trigger
      // This adds visual clarity when an ant has died or changed state
      switch(environmentGrid[ant.position.y][ant.position.x]) {
        
        case Default -> { /* Only including this case so my linter stops bitching */}

        // Ant stepped on home
        case Home -> { 
          if (ant.state == AntState.ReturningHome && !ant.isPaused) {
            ant.isPaused = true;
          }
          else if (ant.isPaused) {
            ant.isPaused = false;
            generateAnts(1); // Spawn a new ant
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
    // Retrieve ant's next move from Ant nextPosition queue
    GridPosition nextPosition = ant.getNextPosition(homePositions);

    // Check if an ant's next move is valid
    if (nextPosition.y >= 0 && nextPosition.y < environmentGrid.length && // Move is within grid boundaries
        nextPosition.x >= 0 && nextPosition.x < environmentGrid.length &&
        !antPositionMap.containsKey(nextPosition) // Move is not to a tile that already contains an ant
    ) {
      // If the move is valid, update the antPosition map to reflect the ant's new position
      antPositionMap.remove(ant.position, ant); // Remove key at ant's previous position to show an ant is no longer positioned there
      antPositionMap.put(nextPosition, ant); // Add a new key-value pair for ant and its new position
      ant.position = nextPosition;
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
      subscriber.update(windowSize, simulationStarted, animationPlaying, environmentGrid, antPositionMap, numberAnts);
    }
  }
}
