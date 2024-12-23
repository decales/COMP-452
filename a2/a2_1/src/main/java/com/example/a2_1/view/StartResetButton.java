package com.example.a2_1.view;

import javafx.scene.control.Button;

public class StartResetButton extends Button {

  public enum ButtonMode { Start, Reset };
  public ButtonMode buttonMode;

  public StartResetButton(double size, boolean animationStarted) {

    buttonMode = (animationStarted) ? ButtonMode.Reset : ButtonMode.Start;
    setText((animationStarted) ? "Reset" : "Start"); 
  }
}
