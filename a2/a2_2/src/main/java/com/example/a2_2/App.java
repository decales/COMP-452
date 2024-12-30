package com.example.a2_2;

import com.example.a2_2.model.Model;
import com.example.a2_2.view.EnvironmentGrid;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) {

    double scale = 0.8;

    double windowSize = Screen.getPrimary().getBounds().getHeight() * scale;

    int gridDimension = 16; // Create gridDimension * gridDimension grid
    int numAnts = 4;
    Model model = new Model(windowSize, gridDimension, numAnts);
    
    HBox root = new HBox();
    root.paddingProperty().set(new Insets(windowSize * 0.025));
    root.setStyle("-fx-background-color: slategrey");
    EnvironmentGrid grid = new EnvironmentGrid();

    root.getChildren().addAll(grid);
    model.addSubscribers(grid);
    model.animate();

    Scene scene = new Scene(root);
    stage.setTitle("");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
