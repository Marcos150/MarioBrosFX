package com.example.mariobrosfx;

public abstract class AnimatedSprite extends Sprite
{
    public int HEIGHT;
    public int WIDTH;
    protected String SPRITE_FILE;
    protected int totalMovements;
    protected int currentMovement;
    protected int currentSprite;
    protected int spriteChangeCounter;
    protected int[][] coordinatesX;
    protected int[][] coordinatesY;
    protected boolean left;
    protected boolean right;

    public void animate(int movement)
    {

    }

    protected void updateSpriteCoordinates()
    {

    }
}