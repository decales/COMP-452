package com.example.a2_2.view;

import com.example.a2_2.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SimulationControlButtons extends VBox {
  
  public enum ButtonMode { Start, Reset, Play, Pause };

  public ButtonMode modeOfSelected; // mode of the button that was last selected determines action in controller
  private ButtonMode simulationMode;
  private ButtonMode animationMode;
  private Label mainLabel;
  private HBox buttonBox;
  private Button startResetButton;
  private Button playPauseButton;

  public SimulationControlButtons(Controller controller) {

    mainLabel = new Label("Simulation control");
    
    buttonBox = new HBox();

    startResetButton = new Button();
    startResetButton.setOnMouseClicked(e -> {
      modeOfSelected = simulationMode;
      controller.onValueChange(this);
    });

    playPauseButton = new Button();
    playPauseButton.setOnMouseClicked(e -> {
      modeOfSelected = animationMode;
      controller.onValueChange(this);
    });

    buttonBox.getChildren().addAll(startResetButton, playPauseButton);

    getChildren().addAll(mainLabel, buttonBox);
  }

  public void update(double windowSize, boolean inputValid, boolean simulationStarted, boolean animationPlaying) {

    mainLabel.setStyle(String.format("-fx-text-fill: black; -fx-font-size: %f", windowSize * 0.015));

    buttonBox.setSpacing(windowSize * 0.0125);

    simulationMode = (simulationStarted) ? ButtonMode.Reset : ButtonMode.Start;
    startResetButton.setText((simulationStarted) ? "Reset" : "Start");
    startResetButton.setStyle(String.format("-fx-text-fill: black; -fx-font-size: %f", windowSize * 0.015));
    startResetButton.setDisable(!inputValid);

    animationMode = (animationPlaying) ? ButtonMode.Pause : ButtonMode.Play;
    playPauseButton.setText((animationPlaying) ? "Pause" : "Play");
    playPauseButton.setStyle(String.format("-fx-text-fill: black; -fx-font-size: %f", windowSize * 0.015));
    playPauseButton.setVisible(simulationStarted);

    setSpacing(windowSize * 0.0075);
  }
}
