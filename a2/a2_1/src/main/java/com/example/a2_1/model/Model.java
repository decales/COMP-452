package com.example.a2_1.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import com.example.a2_1.view.TileSelector.TileSelectorType;

public class Model {
  
  private ArrayList<PublishSubscribe> subscribers;
  private double rootHeight;
  private TileSelectorType currentSelectorType;
  
  private int[][] terrainGrid;
  private int[][] entityGrid;
  private int character_i, character_j;
  private int goal_i, goal_j;

  private PriorityQueue<AStarNode> nextVisit;
  private boolean visited[][];
  private int hCosts[][];
  private int gCosts[][];
  private int[][] traversalDirections;

  public Model(double rootHeight) {

    // UI data
    subscribers = new ArrayList<>();
    this.rootHeight = rootHeight;
    currentSelectorType = TileSelectorType.Terrain;

    // Grid data
    terrainGrid = new int[16][16]; // represents the type of terrain at each tile
    for (int i = 0; i < terrainGrid.length; i++) Arrays.fill(terrainGrid[i], 1);
    
    entityGrid = new int[16][16]; // represents where the start, goal, and obstacles are in the grid
    updateCharacterPos(0, entityGrid.length - 1);
    updateGoalPos(entityGrid.length - 1, 0);

    // A* algorithm data
    nextVisit = new PriorityQueue<>();
    visited = new boolean[16][16];
    gCosts = new int[16][16]; // Cumulative path costs from starting node
    for(int i = 0; i < gCosts.length; i ++) Arrays.fill(gCosts[i], Integer.MAX_VALUE);
    hCosts = new int[16][16]; // Heuristic costs from each node to goal node
    traversalDirections = new int[][] {{0,1}, {0,-1}, {1,0}, {1,1}, {1,-1}, {-1,0}, {-1,1}, {-1,-1}};
  }

  public void updateSelectorType(TileSelectorType type) {
    currentSelectorType = type;
    updateSubcribers();
  }

  public void updateGrid(int i, int j) {
    // 0 -> empty tile
    // 1 -> start/character tile
    // 2 -> goal tile
    // 3 -> obstacle tile

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

  public void startSearch() {
    System.out.println(AStarSearch());
  }

  private int AStarSearch() {
    // Starting node
    gCosts[character_i][character_j] = terrainGrid[character_i][character_j];
    nextVisit.add(new AStarNode(character_i, character_j, 0, null)); // fCost doesn't matter

    while(!nextVisit.isEmpty()) {
      AStarNode currentNode = nextVisit.poll();
      visited[currentNode.i][currentNode.j] = true;

      // Current node is goal node
      if (currentNode.posAt(goal_i, goal_j)) {
        // retracePath(currentNode);
        return gCosts[goal_i][goal_j];
      }

      // Traverse to 8 adjacent nodes, if possible
      for (int[] direction: traversalDirections) {
        int adj_i = currentNode.i + direction[0];
        int adj_j = currentNode.j + direction[1];

        if (adj_i >= 0 && adj_i < terrainGrid.length && adj_j >= 0 && adj_j < terrainGrid[0].length) { // check if adjacent is in bounds
          if (entityGrid[adj_i][adj_j] != 3 && !visited[adj_i][adj_j]) { // check if adjacent is visited or an obstacle
            
            int previousCost = gCosts[adj_i][adj_j];
            int newCost = gCosts[currentNode.i][currentNode.j] + terrainGrid[adj_i][adj_j];
            boolean adjacentInQueue = nextVisit.contains(new AStarNode(adj_i, adj_j, 0, null)); // Jank

            if (newCost < previousCost || !adjacentInQueue) {
              gCosts[adj_i][adj_j] = newCost;
              hCosts[adj_i][adj_j] = calculateHCost(adj_i, adj_j);

              // Create node object and add it to queue
              if (!adjacentInQueue) nextVisit.add(new AStarNode(adj_i, adj_j, gCosts[adj_i][adj_j] + hCosts[adj_i][adj_j], currentNode));
            }
          }
        } 
      }
    }
    return 0; // Goal never reached, no possible path
  }

  public void retracePath(AStarNode goalNode) {

    ArrayList<AStarNode> path = new ArrayList<>();

    AStarNode currentNode = goalNode;
    while(currentNode.previousNode != null) {
      path.add(currentNode);
    }
  }

  private int calculateHCost(int i, int j) {
      // Count minimum nodes away the current is from the goal, the heuristic cost
      int delta_i = Math.abs(i - goal_i);
      int delta_j = Math.abs(j - goal_j);
      int higher = Math.max(delta_i, delta_j);
      int lower = Math.min(delta_i, delta_j);
      return higher - lower; 
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
