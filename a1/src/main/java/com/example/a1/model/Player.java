package com.example.a1.model;

import com.example.a1.model.Model.gameState;

import javafx.scene.image.Image;

import java.util.List;

public class Player extends Entity {

    public int health;
    protected double spawnX, spawnY;
    protected boolean isInvulnerable;
    protected boolean hasDefaultSprite;

    public Player(double width, double height, int health) {
      super("player.png", width, height);
        setTranslateX(-(size / 2));
        setTranslateY(-(size / 2));
        this.health = health;
        spawnX = width / 2;
        spawnY = height / 2;
        isInvulnerable = false;
        move(spawnX, spawnY);
    }

    private void move(double x, double y) {
        posX = x;
        posY = y;
        updateSprite();
    }

    private void setSprite() {
        if (isInvulnerable && hasDefaultSprite) {
            setImage(new Image("playerDamaged.png"));
            hasDefaultSprite = false;
        }
        else if (!isInvulnerable) {
            setImage(new Image("player.png"));
            hasDefaultSprite = true;
        }
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
        move(playerX, playerY);
        setSprite();
        if (state == gameState.doneWin || state == gameState.doneLoss) move(spawnX, spawnY);
    }
}
