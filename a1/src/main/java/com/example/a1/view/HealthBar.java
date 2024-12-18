package com.example.a1.view;

import com.example.a1.model.Enemy;
import com.example.a1.model.Model;
import com.example.a1.model.PublishSubscribe;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.util.List;

public class HealthBar extends TilePane implements PublishSubscribe {

    private Image iconImage;
    private int iconSize;

    public HealthBar(double width, double height) {
      double screenScale = Math.max(width, height);
      iconSize = (int)(screenScale * 0.0525);
      iconImage = new Image("health.png");
      setHgap(10);
      setPadding(new Insets(screenScale * 0.02));
      setMouseTransparent(true);
      setVisible(false);
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
        if (state == Model.gameState.inProgress) {
            getChildren().clear();
            for (int i = 0; i < playerHP; i++) {
                ImageView icon = new ImageView(iconImage);
                icon.setFitWidth(iconSize);
                icon.setFitHeight(iconSize);
                icon.setPreserveRatio(true);
                getChildren().add(icon);
            }
            setVisible(true);
        }
        else setVisible(false);
    }
}
