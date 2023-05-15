package com.example.mariobrosfx;

public abstract class AnimatedSprite extends Sprite
{
    public int HEIGHT;
    public int WIDTH;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    protected String SPRITE_FILE;
    protected int totalMovements;
    protected int currentMovement;
    protected int currentSprite;
    protected int spriteChangeCounter;
    protected int[][] coordinatesX;
    protected int[][] coordinatesY;
    protected boolean left;
    protected boolean right;

    public AnimatedSprite(int width, int height)
    {
        super(width, height);
    }

    public void animate(int movement)
    {
        if (movement != currentMovement)
        {
            currentMovement = movement;
            currentSprite = 0;
            spriteChangeCounter = 0;
        }
        else
        {
            spriteChangeCounter++;
            if (spriteChangeCounter >=
                    Configuration.FRAME_ANIMATION_RATE)
            {
                spriteChangeCounter = 0;
                currentSprite = (byte)((currentSprite + 1) %
                        coordinatesX[currentMovement].length);
            }
        }
        updateSpriteCoordinates();
    }

    protected void updateSpriteCoordinates()
    {
        spriteX = coordinatesX[currentMovement][currentSprite];
        spriteY = coordinatesY[currentMovement][currentSprite];
    }
}