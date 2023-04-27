package com.example.mariobrosfx;

import javafx.scene.image.Image;

public abstract class Sprite
{
    private int height;
    private int width;
    private int x;
    private int y;
    private Image image;

    void moveTo(int x, int y)
    {
    }

    void draw()
    {
    }

    boolean collidesWith(Sprite s)
    {
        return true;
    }
}