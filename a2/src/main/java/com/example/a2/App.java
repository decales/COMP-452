package com.example.a2;

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
        
        StackPane root = new StackPane();
        root.setPrefSize(width, height);

        Scene scene = new Scene(root, width, height);
        stage.setTitle("Nub-evade!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
