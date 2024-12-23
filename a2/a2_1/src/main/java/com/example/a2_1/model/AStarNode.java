package com.example.a2_1.model;

public class AStarNode implements Comparable<AStarNode> {

  // This class exists entirely for the purpose of easily being able to poll nodes from the grid with a PriorityQueue
  // It also allows keeping track of the path of the nodes
  
  protected int i, j;
  protected int fCost;
  protected AStarNode previousNode;

  public AStarNode(int i, int j, int fCost, AStarNode previousNode) {
    this.i = i;
    this.j = j;
    this.fCost = fCost;
    this.previousNode = previousNode;
  }

  public boolean posAt(int i, int j) {
    return this.i == i && this.j == j;
  }

  @Override
  public int compareTo(AStarNode other) {
    return Integer.compare(fCost, other.fCost);
  }

  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    AStarNode other = (AStarNode) obj;
    return this.i == other.i && this.j == other.j;
  }
}
