package com.example.a2_2.model;

import java.util.Objects;

// This class exists so I can use a hashmap to map a given grid position to an Ant
// I also could have just used another 2D array of type Ant, but I'm only now just realizing this in hindsight as a I write this.
// On second thought this also allows me to streamline many operations operate that rely on both y and x of a grid separately

public class GridPosition {
  public int y, x;

  public GridPosition(int y, int x) {
    this.y = y;
    this.x = x;
  }

  @Override
  public boolean equals(Object other) {
    GridPosition otherPosition = (GridPosition) other;
    return (y == otherPosition.y && x == otherPosition.x);
  }

  @Override
  public int hashCode() {
    return Objects.hash(y, x);
  }

}
