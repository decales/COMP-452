package com.example.a2_2.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Ant {

  public enum AntState { SearchingFood, ReturningHome, SearchingWater, Paused }

  public GridPosition position;
  public AntState state;
  private Queue<GridPosition> nextPositionQueue;
  private int[][] directions;
  private Random random;
  
  public Ant(GridPosition position) {

    this.position = position;
    state = AntState.SearchingFood;
    nextPositionQueue = new LinkedList<>();
    directions = new int[][] {{0,1}, {0,-1}, {1,0}, {1,1}, {1,-1}, {-1,0}, {-1,1}, {-1,-1}};
    random = new Random();
  }

  public GridPosition getNextPosition(ArrayList<GridPosition> homePositions) {
    if (nextPositionQueue.isEmpty()) {
      switch(state) {
        // Pause state used to keep an ant in place so it is visually clear when it dies or picks up food after a frame update
        case Paused -> nextPositionQueue.add(new GridPosition(position.y, position.x)); 
        case SearchingFood, SearchingWater -> roam();
        case ReturningHome -> path(homePositions);
      }
    }
    return nextPositionQueue.poll();
  }


  private void roam() {
    // Choose a random adjacent tile and add it to next position queue
    int[] randomDirection = directions[random.nextInt(directions.length)];
    nextPositionQueue.add(new GridPosition(position.y + randomDirection[0], position.x + randomDirection[1]));
  }

    
  public void path(ArrayList<GridPosition> homePositions) {
    // Path directly to path to nearest homePosition using BFS and add each tile along path to next position queue
      
    Queue<GridPosition> queue = new LinkedList<>();
    HashMap<GridPosition, GridPosition> previousPosition = new HashMap<>(); // To reconstruct the path
    HashSet<GridPosition> visited = new HashSet<>();

    // BFS started from ant's current position
    queue.add(position);
    visited.add(position);

    GridPosition currentPosition = null;

    while (!queue.isEmpty()) {
      currentPosition = queue.poll();

      // Nearest home found, exit early
      if (homePositions.contains(currentPosition)) break;

      // Explore adjacent cells
      for (int[] direction : directions) {
        int newY = currentPosition.y + direction[0];
        int newX = currentPosition.x + direction[1];

        GridPosition adjPosition = new GridPosition(newY, newX);
        if (!visited.contains(adjPosition)) {
          visited.add(adjPosition);
          queue.add(adjPosition);
          previousPosition.put(adjPosition, currentPosition);
        }
      }
    }

    // Reconstruct the path to the nearest home
    ArrayList<GridPosition> pathToClosestHome = new ArrayList<>();

    GridPosition nextPosition = currentPosition;
    while (!nextPosition.equals(position)) {
      pathToClosestHome.add(0, nextPosition);
      nextPosition = previousPosition.get(nextPosition);
    }
    nextPositionQueue.addAll(pathToClosestHome);
  }
}
