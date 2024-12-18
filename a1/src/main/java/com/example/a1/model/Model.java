package com.example.a1.model;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;

public class Model implements PublishSubscribe {

    public enum gameState { notStarted, inProgress, doneWin, doneLoss }

    public double width;
    public double height;
    public Player player;
    private int numRedEnemies;
    private int numBlueEnemies;
    public List<Enemy> enemies;
    protected List<PublishSubscribe> subscribers;
    private gameState state;
    private AnimationTimer timer;
    private int timeLimit;
    private int timeRemaining;
    private long lastTimeUpdate;
    private long lastTimeHit;

    public Model(double width, double height, int numRedEnemies, int numBlueEnemies, int playerHP, int timeLimit) {
        this.width = width;
        this.height = height;
        
        this.numRedEnemies = numRedEnemies;
        this.numBlueEnemies = numBlueEnemies;
        enemies = new ArrayList<>();
        player = new Player(width, height, playerHP);
        subscribers = new ArrayList<>();
        subscribers.add(player);
        
        this.timeLimit = timeLimit;
        timeRemaining = timeLimit + 1;
        state = gameState.notStarted;
    }

    public void start() {
        state = gameState.inProgress;
        initEnemies(numRedEnemies, numBlueEnemies);
        timer = new AnimationTimer() {
            public void handle(long time) {
                updateTime(time);
                detectPlayerCollision(time);
                checkReset();
                update(player.posX, player.posY, player.health, player.isInvulnerable, timeRemaining, state, enemies);
            }
        };
        timer.start();
    }

    private void updateTime(long time) {
        if (time - lastTimeUpdate >= 1000000000) {
            timeRemaining--;
            lastTimeUpdate = time;
        }
    }

    private void initEnemies(int numRed, int numBlue) {
        for (int i = 0; i < numRed; i++) {
            RedEnemy enemy = new RedEnemy(width, height);
            enemies.add(enemy);
        }
        for (int i = 0; i < numBlue; i++) {
            BlueEnemy enemy = new BlueEnemy(width, height);
            enemies.add(enemy);
        }
        subscribers.addAll(enemies);
    }

    private void detectPlayerCollision(long time) {
        if (!player.isInvulnerable) {
            for (Enemy enemy : enemies) {
                if (enemy.contains(player.posX, player.posY)) {
                    player.health--;
                    player.isInvulnerable = true;
                    lastTimeHit = time;
                    break;
                }
            }
        }
        else if (time - lastTimeHit >= 1000000000) { // 1s invulnerability time after hit
            player.isInvulnerable = false;
        }
    }

    public void addSubscribers(PublishSubscribe... subscribers) {
        this.subscribers.addAll(List.of(subscribers));
    }

    public void checkReset() {
        if (player.health <= 0 || timeRemaining <= 0) {
            if (player.health <= 0) state = gameState.doneLoss;
            else state = gameState.doneWin;
            player.health = 3;
            timeRemaining = timeLimit + 1;
            lastTimeHit = 0;
            lastTimeUpdate = 0;
            subscribers.removeAll(enemies);
            enemies.clear();
            timer.stop();
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
