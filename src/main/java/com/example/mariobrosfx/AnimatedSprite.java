package com.example.mariobrosfx;

/**
 * Abstract class that defines all the elements with animated sprites
 */
public abstract class AnimatedSprite extends Sprite
{
    protected int totalMovements;
    protected int currentMovement;
    protected int currentSprite;
    protected int spriteChangeCounter;
    protected int[][] coordinatesX;
    protected int[][] coordinatesY;
    protected boolean left;
    protected boolean right;

    /**
     * Constructor of AnimatedSprite class
     * @param width element width
     * @param height element height
     */
    public AnimatedSprite(int width, int height)
    {
        super(width, height);
    }

    /**
     * Method that animates the element
     * @param movement direction of the movement
     */
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

    /**
     * Method that updates the current sprite when animating
     */
    protected void updateSpriteCoordinates()
    {
        spriteX = coordinatesX[currentMovement][currentSprite];
        spriteY = coordinatesY[currentMovement][currentSprite];
    }
}