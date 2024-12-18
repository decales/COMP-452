package com.example.a1.view;

import com.example.a1.model.Enemy;
import com.example.a1.model.Model;
import com.example.a1.model.PublishSubscribe;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;

import java.util.List;

public class StartButton extends Button implements PublishSubscribe {

    public StartButton(Model model) {
      int fontSize = (int)(Math.max(model.width, model.height) * 0.025);
        setText("Start");
        setStyle(String.format("-fx-font-size: %dpx; -fx-background-color: lightblue; -fx-text-fill: white;", fontSize));
        setOnAction(_ -> model.start());
        DropShadow shadow = new DropShadow();
        shadow.setRadius(1);
        shadow.setSpread(0.1);
        setEffect(shadow);
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
        setVisible(state == Model.gameState.notStarted);
    }
}
