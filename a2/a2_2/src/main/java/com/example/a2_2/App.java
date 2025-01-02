package com.example.a2_2;

import com.example.a2_2.model.Model;
import com.example.a2_2.view.ControlMenu;
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

    // Init model and controller
    Model model = new Model(windowSize);
    Controller controller = new Controller(model);

    // Init visual components
    HBox root = new HBox();
    root.paddingProperty().set(new Insets(windowSize * 0.025));
    root.setSpacing(windowSize * 0.025);
    root.setStyle("-fx-background-color: slategrey");

    EnvironmentGrid grid = new EnvironmentGrid();
    ControlMenu controlMenu = new ControlMenu(controller);

    root.getChildren().addAll(grid, controlMenu);
    model.addSubscribers(grid, controlMenu);

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
