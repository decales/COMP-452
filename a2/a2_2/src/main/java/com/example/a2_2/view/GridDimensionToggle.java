package com.example.a2_2.view;

import java.util.List;

import com.example.a2_2.Controller;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class GridDimensionToggle extends VBox {

  private Label mainLabel;
  private ToggleGroup radioButtons;
  private VBox radioButtonBox;
  
  public GridDimensionToggle(Controller controller) {

    mainLabel = new Label("Grid dimensions");

    radioButtonBox = new VBox();
    radioButtons = new ToggleGroup();

    for (Integer i : List.of(8, 16, 32)) {
      RadioButton button = new RadioButton(String.format("%d x %d", i, i));
      button.setToggleGroup(radioButtons);
      if (i == 16) button.setSelected(true);
      radioButtonBox.getChildren().add(button);
    }
    radioButtons.selectedToggleProperty().addListener((o, oldVal, newVal) -> controller.onValueChange(this));
    getChildren().addAll(mainLabel, radioButtonBox);
  }

  public int getSelectionValue() {
    RadioButton button = (RadioButton) radioButtons.getSelectedToggle();
    return Integer.parseInt(button.getText().split(" ")[0]); // Defensive programming? Never heard of it.
  }

  public void update(double windowSize, boolean simulationStarted) {

    mainLabel.setStyle(String.format("-fx-text-fill: black; -fx-font-size: %f", windowSize * 0.015));

    radioButtonBox.setSpacing(windowSize * 0.005);
    for (Node node : radioButtonBox.getChildren()) {
      RadioButton button = (RadioButton) node;
      button.setStyle(String.format("-fx-text-fill: black; -fx-font-size: %f", windowSize * 0.015));
    }
    setSpacing(windowSize * 0.0075);
    setDisable(simulationStarted);
  }
}
