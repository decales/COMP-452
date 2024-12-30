package com.example.a2_2;

import com.example.a2_2.model.Model;
import com.example.a2_2.view.EnvironmentGrid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) {

    double scale = 0.8;
    double width = Screen.getPrimary().getBounds().getWidth() * scale;
    double height = Screen.getPrimary().getBounds().getHeight() * scale;

    
    int gridDimension = 32; // Create gridDimension * gridDimension grid
    int numAnts = 4;
    Model model = new Model(height, gridDimension, numAnts);
    
    StackPane root = new StackPane();
    EnvironmentGrid grid = new EnvironmentGrid();

    root.getChildren().addAll(grid);
    model.addSubscribers(grid);
    model.animate();

    Scene scene = new Scene(root);
    stage.setTitle("");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
