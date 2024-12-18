package com.example.a1.model;

public abstract class Enemy extends Entity {

    protected double deltaX, deltaY;

    public Enemy(String spriteFile, double width, double height) {
      super(spriteFile, width, height);
        setTranslateX(-(size / 2));
        setTranslateY(-(size / 2));
        deltaR = rng.nextDouble(2) - 1;
        spawn(width, height);
    }
    
    protected void spawn(double width, double height) {
      // Keep placing an enemy until its position is in the outer zone of the screen
      do posX = rng.nextDouble(width); while (posX > width / 3 && posX < 2 * width / 3);
      do posY = rng.nextDouble(height); while (posY > height / 3 && posY < 2 * height / 3);
      updateSprite();
    }

    protected void rotate() {
      // For rotation animation
      angle += deltaR;
      setRotate(angle % 360);
    }

    protected void move() {
      posX += deltaX * velocity;
      posY += deltaY * velocity;
      updateSprite();
    }

    protected double distance(double x, double y) {
      // Determine the distance to another entity
      return Math.sqrt(Math.pow(x - posX, 2) + Math.pow(y - posY, 2));
    }

    @Override
    public boolean contains(double x, double y) {
      // Determine if another entity is within the current
      return distance(x, y) <= size;
    }
}
