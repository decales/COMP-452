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

        Model model = new Model(width, height, 32, 6);
        
        StackPane root = new StackPane();
        root.setPrefSize(width, height);

        EnvironmentGrid grid = new EnvironmentGrid();

        root.getChildren().addAll(grid);

        model.addSubscribers(grid);

        model.animate();

        Scene scene = new Scene(root, width, height);
        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
