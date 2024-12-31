package com.example.a2_2.model;

import java.util.HashMap;

import com.example.a2_2.view.EnvironmentTile.TileType;

public interface PublishSubscribe {

  void update(double windowSize, boolean simulationStarted, TileType[][] environmentGrid, HashMap<GridPosition, Ant> antPositionMap);
}
