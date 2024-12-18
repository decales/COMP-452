package com.example.a1;

import com.example.a1.model.Model;
import com.example.a1.view.*;
import javafx.application.Application;
import javafx.geometry.Pos;
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
        
        // Init model - width, height, numRedEnemies, numBlueEnemies, playerHP, timeLimit
        Model model = new Model(width, height, 1, 4, 3, 15);

        StackPane root = new StackPane();
        root.setPrefSize(width, height);

        // UI Components
        MainView mainView = new MainView(model);
        StartButton startButton = new StartButton(model);
        HealthBar healthBar = new HealthBar(width, height);
        TimeLabel timeLabel = new TimeLabel(width, height);
        EndView endView = new EndView(model);

        model.addSubscribers(mainView, startButton, healthBar, timeLabel, endView);
        root.getChildren().addAll(mainView, startButton, healthBar, timeLabel, endView);
        root.setAlignment(timeLabel, Pos.TOP_CENTER);

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
