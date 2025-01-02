package com.example.a2_2.view;

import com.example.a2_2.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AntNumberEntry extends VBox {

  private Label mainLabel;
  private TextField textField;
  private HBox textFieldBox;
  private Label maxLabel;

  public AntNumberEntry(Controller controller) {

    mainLabel = new Label("Initial number of ants");

    textFieldBox = new HBox();
    textFieldBox.setAlignment(Pos.CENTER_LEFT);

    textField = new TextField();
    textField.setAlignment(Pos.CENTER);
    textField.textProperty().addListener((o, oldVal, newVal) -> controller.onValueChange(this));

    maxLabel = new Label();
    
    textFieldBox.getChildren().addAll(textField, maxLabel);

    getChildren().addAll(mainLabel, textFieldBox);
  }

  public int getInputValue() {
    try { // Check if textField has a valid parseable string
      return Integer.parseInt(textField.getText());
    }
    catch (NumberFormatException e) {
      return 0; 
    }
  }

  public void update(double windowSize, int maxAntValue, boolean simulationStarted) {

    mainLabel.setStyle(String.format("-fx-text-fill: black; -fx-font-size: %f", windowSize * 0.015));

    textFieldBox.setSpacing(windowSize * 0.0125); 
    textField.setPrefWidth(windowSize * 0.0625);
    textField.setStyle(String.format("-fx-font-size: %f", windowSize * 0.015));

    maxLabel.setText(String.format(" (max. %d)", maxAntValue));
    maxLabel.setStyle(String.format("-fx-text-fill: %s; -fx-font-size: %f", (getInputValue() > maxAntValue) ? "red" : "black", windowSize * 0.015));

    setSpacing(windowSize * 0.0075);
    setDisable(simulationStarted);
  }
}
