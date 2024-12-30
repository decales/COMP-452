package com.example.a2_2.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Ant {

  public enum AntState { SearchingFood, ReturningHome, SearchingWater}

  public GridPosition position;
  public AntState state;
  public boolean isPaused;
  public double rotationAngle;
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
    // isPaused used to keep an ant in place so it is visually clear when it dies or picks up food after a frame update
    if (isPaused) { return new GridPosition (position.y, position.x); };
    if (nextPositionQueue.isEmpty()) {
      switch(state) {
        case SearchingFood, SearchingWater -> roam();
        case ReturningHome -> path(homePositions);
      }
    }
    GridPosition nextPosition = nextPositionQueue.poll();
    getRotationAngle(nextPosition);
    return nextPosition;
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

  public void getRotationAngle(GridPosition nextPosition) {
    // Used solely for cosmetic purposes so the ant is always facing the direction is travelled on each frame update
    int deltaY = nextPosition.y - position.y;
    int deltaX = nextPosition.x - position.x;

    if (deltaY == -1 && deltaX == 0) rotationAngle = 0;   // Up
    else if (deltaY == -1 && deltaX == 1) rotationAngle = 45;  // Up-right
    else if (deltaY == 0 && deltaX == 1) rotationAngle = 90;   // Right
    else if (deltaY == 1 && deltaX == 1) rotationAngle = 135;  // Down-right
    else if (deltaY == 1 && deltaX == 0) rotationAngle = 180;  // Down
    else if (deltaY == 1 && deltaX == -1) rotationAngle = 225; // Down-left
    else if (deltaY == 0 && deltaX == -1) rotationAngle = 270; // Left
    else if (deltaY == -1 && deltaX == -1) rotationAngle = 315; // Up-left
  }
}
