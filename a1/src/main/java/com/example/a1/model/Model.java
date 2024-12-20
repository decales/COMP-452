package com.example.a1.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Model implements PublishSubscribe {

    public enum gameState { notStarted, inProgress, doneWin, doneLoss }

    public double width, height;
    public Player player;
    private int numRedEnemies, numBlueEnemies;
    public List<Enemy> enemies;
    protected List<PublishSubscribe> subscribers;
    private gameState state;
    private Timeline gameTimer;
    private Timeline countdownTimer;
    private int timeLimit;
    private int timeRemaining;
    private long timeHit;

    public Model(double width, double height, int numRedEnemies, int numBlueEnemies, int playerHP, int timeLimit) {

      // Save screen width and height for scaling purposes
      this.width = width;
      this.height = height;
      
      // Enemy data
      this.numRedEnemies = numRedEnemies;
      this.numBlueEnemies = numBlueEnemies;
      enemies = new ArrayList<>();
      player = new Player(width, height, playerHP);
      subscribers = new ArrayList<>();
      subscribers.add(player);
      
      this.timeLimit = timeLimit * 1000;
      timeRemaining = this.timeLimit;
      state = gameState.notStarted;

      // Main game animation and event timer
      gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        detectPlayerCollision();
        checkReset();
        update(player.posX, player.posY, player.health, player.isInvulnerable, timeRemaining, state, enemies);
      }));
      gameTimer.setCycleCount(Animation.INDEFINITE);
      gameTimer.setRate(60);

      // Countdown timer (millis)
      countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> timeRemaining--));
      countdownTimer.setCycleCount(timeRemaining);
      countdownTimer.setRate(1000);
    }

    public void start() {
      state = gameState.inProgress;
      initEnemies(numRedEnemies, numBlueEnemies);
      gameTimer.play();
      countdownTimer.play();
    }

    private void initEnemies(int numRed, int numBlue) {
      for (int i = 0; i < numRed; i++) enemies.add(new RedEnemy(width, height));
      for (int i = 0; i < numBlue; i++) enemies.add(new BlueEnemy(width, height));
      subscribers.addAll(enemies);
    }

    private void detectPlayerCollision() {
      if (!player.isInvulnerable) {
        for (Enemy enemy : enemies) {
          if (enemy.contains(player.posX, player.posY)) { // player is hit
            player.health--;
            player.isInvulnerable = true;
            timeHit = timeRemaining;
            break;
          }
        }
      }
      else if (timeHit - timeRemaining >= 1000) { // 1s invulnerability time after hit
        player.isInvulnerable = false;
      }
    }

    public void addSubscribers(PublishSubscribe... subscribers) {
      this.subscribers.addAll(List.of(subscribers));
    }

    public void checkReset() {
      // Check if end conditions are met, and if so, reset game values
      if (player.health <= 0 || timeRemaining <= 0) {
        if (player.health <= 0) state = gameState.doneLoss;
        else state = gameState.doneWin;
        player.health = 3;
        player.isInvulnerable = false;
        timeRemaining = timeLimit;
        timeHit = 0;
        subscribers.removeAll(enemies);
        enemies.clear();
        gameTimer.stop();
        countdownTimer.stop();
      }
    }

    public void update(double playerX, double playerY) {
      player.posX = playerX;
      player.posY = playerY;
    }

    @Override
    public void update(
        double playerX,
        double playerY,
        int playerHP,
        boolean playerInvulnerable,
        long timeRemaining,
        gameState state,
        List<Enemy> enemies)
    {
      subscribers.forEach(subscriber -> {
        subscriber.update(playerX, playerY, playerHP, playerInvulnerable, timeRemaining, state, enemies);
      });
    }
}
