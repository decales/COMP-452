package com.example.a1.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

public class Entity extends ImageView implements PublishSubscribe {

    protected Random rng;
    protected double posX, posY;
    protected double velocity;
    protected double angle;
    protected double deltaR;
    protected double size;

    public Entity(String spriteFile, double width, double height) {
      rng = new Random();
      size = Math.max(width, height) * 0.08;
      setImage(new Image(spriteFile));
      setFitWidth(size);
      setFitHeight(size);
      setPreserveRatio(true);
    }

    protected void updateSprite() {
        setLayoutX(posX);
        setLayoutY(posY);
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
    {}
}
