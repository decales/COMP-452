package com.example.a1.view;

import com.example.a1.model.Enemy;
import com.example.a1.model.Model;
import com.example.a1.model.PublishSubscribe;
import com.example.a1.model.Model.gameState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.List;

public class MainView extends Pane implements PublishSubscribe {

    private Model model;
    private Background defaultBackground;
    private Background damageBackground;
    private boolean hasDefaultBackground;

    public MainView(Model model) {
        this.model = model;

        BackgroundImage defaultImage = new BackgroundImage(
            new Image("background.png"),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        defaultBackground = new Background(defaultImage);

        BackgroundImage damageImage = new BackgroundImage(
            new Image("background_damage.png"),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.DEFAULT.getWidth(), BackgroundSize.DEFAULT.getHeight(), true, true, true, true));
        damageBackground = new Background(damageImage);

        setPrefSize(model.width, model.height);
        setBackground(defaultBackground);
        hasDefaultBackground = true;
    }

    private void updateBackground(double millis) {
      hasDefaultBackground = false;
      setBackground(damageBackground);
      new Timeline(new KeyFrame(Duration.millis(millis), _ -> setBackground(defaultBackground))).play();
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
        if (getChildren().isEmpty()) {
            getChildren().addAll(this.model.enemies);
            getChildren().add(this.model.player);
        }
        if (state == Model.gameState.inProgress) {
            setOnMouseMoved(event -> model.update(event.getX(), event.getY()));
            setCursor(Cursor.NONE);
            if (playerInvulnerable && hasDefaultBackground) updateBackground(500);
            else if (!playerInvulnerable) hasDefaultBackground = true;
        } 
        else if (state == Model.gameState.doneWin || state == gameState.doneLoss) {
            getChildren().clear();
            setOnMouseMoved(_ -> {});
            setCursor(Cursor.DEFAULT);
        }
    }
}
