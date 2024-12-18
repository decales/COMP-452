package com.example.a1.view;

import com.example.a1.model.Enemy;
import com.example.a1.model.Model;
import com.example.a1.model.PublishSubscribe;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.List;

public class EndView extends VBox implements PublishSubscribe {

  private Label endLabel;
  private Button replayButton;

    public EndView(Model model) {

      setAlignment(Pos.CENTER);
      setSpacing(20.0);
      setLayoutX(model.width / 2);
      setLayoutY(model.height / 2);
      setVisible(false);

      // Post game replay button
      replayButton = new Button("Play again");
      int buttonSize = (int)(Math.max(model.width, model.height) * 0.015);
      replayButton.setStyle("-fx-font-size: " + buttonSize + "; -fx-background-color: lightgrey; -fx-text-fill: white;");
      replayButton.setOnAction(_ -> {
        setVisible(false);
        model.start();
      });

      // Post game message
      endLabel = new Label("");
      int labelSize = (int)(Math.max(model.width, model.height) * 0.035);
      endLabel.setFont(Font.font(labelSize));
      
      DropShadow shadow = new DropShadow();
      shadow.setRadius(1);
      shadow.setSpread(0.1);
      replayButton.setEffect(shadow);

      getChildren().addAll(endLabel, replayButton);
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
        if (state == Model.gameState.doneLoss) {
            endLabel.setText("You lose");
            endLabel.setTextFill(Color.SALMON);
            setVisible(true);
        }
        else if (state == Model.gameState.doneWin) {
            endLabel.setText("You win");
            endLabel.setTextFill(Color.SKYBLUE);
            setVisible(true);
        }
    }
}
