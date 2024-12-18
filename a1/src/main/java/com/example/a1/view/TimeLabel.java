package com.example.a1.view;

import com.example.a1.model.Enemy;
import com.example.a1.model.Model;
import com.example.a1.model.PublishSubscribe;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;

public class TimeLabel extends Label implements PublishSubscribe {

    public TimeLabel(double width, double height) {
        
      double screenScale = Math.max(width, height);

        int fontSize = (int)(screenScale * 0.035);
        setPadding(new Insets(screenScale * 0.01));
        setFont(Font.font("", fontSize));
        setTextFill(Color.web("#dd7bed"));
        setMouseTransparent(true);
        setVisible(false);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(1.5);
        shadow.setSpread(0.2);
        setEffect(shadow);
    }

    @Override
    public void update(double playerX, double playerY, int playerHP, boolean playerInvulnerable, long timeRemaining, Model.gameState state, List<Enemy> enemies) {
        if (state == Model.gameState.inProgress) {
            setText(String.valueOf(timeRemaining));
            setVisible(true);
        }
        else setVisible(false);
    }
}
