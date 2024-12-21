package com.example.a2_1;

import javax.swing.GroupLayout.Alignment;

import com.example.a2_1.model.Model;
import com.example.a2_1.view.GridBuilderMenu;
import com.example.a2_1.view.GridView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        double displayHeight = Screen.getPrimary().getBounds().getHeight();

        double displayRatio = 0.8;
        double rootPaddingRatio = 0.05;        
        double gridSpacingRatio = 0.05;

        double rootPadding = (rootPaddingRatio * displayHeight * displayRatio) / 2;
        double tileSpacing = (gridSpacingRatio * displayHeight * displayRatio) / 16;
        double gridHeight = (displayHeight * displayRatio) - rootPadding * 2;

        // Initialize model
        Model model = new Model(gridHeight);
        
        // Initialize UI components
        HBox root = new HBox();
        root.paddingProperty().set(new Insets(rootPadding));

        GridView gridView = new GridView(tileSpacing);
        GridBuilderMenu gridMenu = new GridBuilderMenu();

        root.getChildren().addAll(gridView, gridMenu);
        model.addSubscribers(gridView, gridMenu);

        Scene scene = new Scene(root);
        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
