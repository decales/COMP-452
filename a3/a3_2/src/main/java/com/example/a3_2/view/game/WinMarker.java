package com.example.a3_2.view.game;

import java.io.File;
import com.example.a3_2.model.Fighter.FighterSide;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinMarker extends ImageView {

  private Image[] sprites;

  public WinMarker(FighterSide side) {
    File[] spriteFiles = new File(getClass().getResource("/game/wins/").getPath()).listFiles();
    sprites = new Image[spriteFiles.length];
    for (int i = 0; i < sprites.length; i++) sprites[i] = new Image(spriteFiles[i].toURI().toString());

    // flip sprite for right bar to maintain symmetry
    if (side == FighterSide.right) setScaleX(-1); 
    setPreserveRatio(true);
  }


  public void update(double viewSize, int wins) {
    setFitWidth(viewSize * 0.08125);

    if (wins >= 11) setScaleX(1);
    if (wins >= sprites.length) setImage(sprites[sprites.length - 1]);
    else setImage(sprites[wins]);
  }
}
