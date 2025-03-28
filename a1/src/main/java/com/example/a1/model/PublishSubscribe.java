package com.example.a1.model;

import java.util.List;

public interface PublishSubscribe {

  // publish-subcribe function to pass game data to subscribers
  void update(
    double playerX,
    double playerY,
    int playerHP,
    boolean playerInvulnerable,
    long timeRemaining,
    Model.gameState state,
    List<Enemy> enemies
    );
}
