package com.example.a1.model;

import java.util.List;

public class RedEnemy extends Enemy {

    double normalVelocity;
    double aggroVelocity;

    public RedEnemy(double width, double height) {
      super("redEnemy.png", width, height);
      normalVelocity = Math.max(width, height) * 0.00125 ;
      aggroVelocity = normalVelocity * 1.25;
      deltaR = 1.5;
    }

    private void seek(double playerX, double playerY) {
        double distanceToPLayer = distance(playerX, playerY);
        deltaX = (playerX - posX) / distanceToPLayer;
        deltaY = (playerY - posY) / distanceToPLayer;
    }

    private void avoid(List<Enemy> enemies) {
      for (Enemy enemy : enemies) { // Calculate using positions of all enemies on screen
        if (enemy == this) continue; // skip self 

        double distanceToEnemyX = this.posX - enemy.posX;
        double distanceToEnemyY = this.posY - enemy.posY;
        double distanceToEnemy = Math.sqrt(Math.pow(distanceToEnemyX, 2) + Math.pow(distanceToEnemyY, 2));

        double separationMod = 1.5; // Value to determine the boundary at which the repel should begin
        double repelMod = 1.2; // Value to adjust the strength of the repel force

        if (distanceToEnemy < size * separationMod) { // Repel if an enemy is at or within repel boundary
            double repellingForceX = (distanceToEnemyX / distanceToEnemy) * repelMod;
            double repellingForceY = (distanceToEnemyY / distanceToEnemy) * repelMod;
            deltaX += repellingForceX;
            deltaY += repellingForceY;
        }
      }
    }

    private void face() {
      // Faces the enemy sprite in the direction of the player
      angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
      setRotate(angle);
    }

    private void normalMode(List<Enemy> enemies, double playerX, double playerY) {
      // Enemy moves at normal speed and paths around other enemies
      velocity = normalVelocity;
      seek(playerX, playerY); 
      avoid(enemies);
      rotate();
    }

    private void aggroMode(List<Enemy> enemies, double playerX, double playerY) {
      // Enemy speeds up and ignore collision with other enemies
      velocity = aggroVelocity;
      seek(playerX, playerY);
      face();
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
    if (timeRemaining > 5) normalMode(enemies, playerX, playerY);
    else aggroMode(enemies, playerX, playerY);
    move();
  }
}
