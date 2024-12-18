package com.example.a1.model;

import java.util.List;

public class BlueEnemy extends Enemy {

    private double maxX, maxY;

    public BlueEnemy(double width, double height) {
      super("blueEnemy.png", width, height);
        velocity = rng.nextDouble(0.5) + 0.5 * Math.max(width, height) * 0.00125;
        deltaX = ((rng.nextBoolean()) ? 1 : -1) * velocity;
        deltaY = ((rng.nextBoolean()) ? 1 : -1) * velocity;
        maxX = width;
        maxY = height;
    }

    private void wander() {
        if ((posX >= maxX || posX < 0) || (posY >= maxY || posY < 0)) {
          if (posX >= maxX || posX < 0) deltaX = -deltaX;
          else deltaY = -deltaY;
          deltaR =  rng.nextDouble(2) - 1;
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
