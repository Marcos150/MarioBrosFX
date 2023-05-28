package com.example.mariobrosfx;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class that defines all the elements with a sprite
 */
public abstract class Sprite
{
    protected int height;
    protected int width;
    protected int x;
    protected int y;
    protected int spriteX;
    protected int spriteY;
    protected Image image;

    /**
     * Constructor of Sprite class
     * @param width element width
     * @param height element height
     */
    public Sprite(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * Method that moves the element to a specific position
     * @param x new x coordinate
     * @param y new y coordinate
     */
    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Method that draws the sprite
     * @param gc GraphicsContext element of the game canvas
     */
    public void draw(GraphicsContext gc)
    {
        gc.drawImage(image, spriteX, spriteY,
                width, height, x, y, width, height);
    }

    /**
     * Method that returns the boundaries of the element
     * @return boundaries of the element
     */
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Method that returns if this sprite is colliding with other
     * @param s other sprite
     * @return if this sprite is colliding with other sprite
     */
    public boolean collidesWith(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }

    /**
     * Method that returns the x coordinate
     * @return x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Method that returns the y coordinate
     * @return y coordinate
     */
    public int getY()
    {
        return y;
    }
}