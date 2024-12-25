package com.example.a2_1;

import com.example.a2_1.model.Model;
import com.example.a2_1.view.GridTile;
import com.example.a2_1.view.StartResetButton;
import com.example.a2_1.view.TileSelector;
import com.example.a2_1.view.StartResetButton.ButtonMode;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

public class Controller {
  
  private Model model;

  public Controller(Model model) {
    this.model = model;
  }

  public void handleMouseClicked(MouseEvent event) {

    // Determine model action based on what type of object is clicked
    switch(event.getSource()) {
      case TileSelector source -> model.updateSelectorType(source.selectorType);
      case GridTile source -> model.updateGrid(source.i, source.j);
      case RadioButton source -> model.setAnimationSpeed(Integer.parseInt(source.getText()));
      case StartResetButton source -> {
        if (source.buttonMode == ButtonMode.Start) model.start();
        else model.reset();
      }
      case Button source -> model.clearGrid(); 
      default -> { /* This should never happen*/ }
    }
  }
}
