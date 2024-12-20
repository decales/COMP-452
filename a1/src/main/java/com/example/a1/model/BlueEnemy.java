package com.example.a1.model;

import java.util.List;

public class BlueEnemy extends Enemy {

    private double maxX, maxY;

    public BlueEnemy(double width, double height) {
      super("blueEnemy.png", width, height);
        velocity = rng.nextDouble(0.5) + 0.5 * Math.max(width, height) * 0.0025;
        deltaX = ((rng.nextBoolean()) ? 1 : -1) * velocity;
        deltaY = ((rng.nextBoolean()) ? 1 : -1) * velocity;
        maxX = width;
        maxY = height;
    }

    private void wander() {
      // Check if enemy is outside borders of screen
      if ((posX >= maxX || posX < 0) || (posY >= maxY || posY < 0)) {
        if (posX >= maxX || posX < 0) deltaX = -deltaX; // Invert x if position if needed
        else deltaY = -deltaY; // Invert y position if needed
        deltaR = rng.nextDouble(2) - 1; // deltaR is the value that allows the enemy sprite to rotate
      }
    }

    @Override
    public void update(
        double playerX,
        double playerY,
        int playerHP,
        boolean playerInvulnerable,
        long timeRemaining,
        Model.gameState state,
        List<Enemy> enemies) 
    {
      rotate();
      wander();
      move();
    }
}
