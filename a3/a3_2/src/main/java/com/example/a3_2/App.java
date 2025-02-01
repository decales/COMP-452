package com.example.a3_2;

import com.example.a3_2.model.Model;
import com.example.a3_2.view.Arena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        // Screen information
        double displayWidth = Screen.getPrimary().getBounds().getWidth();
        double displayHeight = Screen.getPrimary().getBounds().getHeight();
        double displayRatio = 0.8;

        double viewWidth = displayWidth * displayRatio;
        double viewHeight = displayHeight * displayRatio;

        Model model = new Model(viewWidth, viewHeight);
        Controller controller = new Controller(model);

        // // Initialize UI components
        HBox root = new HBox();
        Arena arena = new Arena();
        arena.setPrefSize(displayWidth, displayHeight);

        root.getChildren().addAll(arena);
        model.addSubscribers(arena);

        Scene scene = new Scene(root, viewWidth, viewHeight);
        scene.setOnKeyPressed(controller::handleKeyPressed);
        scene.setOnKeyReleased(controller::handleKeyReleased);
        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
