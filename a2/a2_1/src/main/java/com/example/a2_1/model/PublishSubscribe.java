package com.example.a2_1.model;

import com.example.a2_1.view.TileSelector.TileSelectorType;

public interface PublishSubscribe {

  void update(
      double gridHeight,
      int[][] terrainGrid,
      int[][] entityGrid,
      int[][] visited,
      TileSelectorType currentSelectorType,
      boolean animationStarted,
      int pathLength);
}
