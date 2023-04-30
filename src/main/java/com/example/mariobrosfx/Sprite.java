package com.example.mariobrosfx;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite
{
    private int height;
    private int width;
    private int x;
    private int y;
    private int spriteX;
    private int spriteY;
    private Image image;

    public Sprite(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw(GraphicsContext gc)
    {
        gc.drawImage(image, spriteX, spriteY,
                width, height, x, y, width, height);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(x, y, width, height);
    }

    boolean collidesWith(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}