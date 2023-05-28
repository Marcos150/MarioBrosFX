package com.example.mariobrosfx;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class that defines the playable character
 */
public class Character extends AnimatedSprite
{
    private int currentGravity;
    public static final int CHARACTER_WIDTH = 16;
    public static final int CHARACTER_HEIGHT = 32;
    private static final String IMAGE_PATH = "src/main/resources/com/example/" +
            "mariobrosfx/sprites/spritesAndar.png";
    private String spriteJumpLeft;
    private String spriteJumpRight;
    private String spriteStillLeft;
    private String spriteStillRight;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private int lastDirection;
    //Platform where the player is standing
    private Platform currentPlatform;
    private boolean canFall;
    private boolean canJump;

    /**
     * Constructor of character class
     */
    public Character()
    {
        super(CHARACTER_WIDTH, CHARACTER_HEIGHT);
        try
        {
            image = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        spriteJumpLeft = "src/main/resources/com/example/mariobrosfx/sprites/" +
                "spriteSaltoIzquierda.png";
        spriteJumpRight = "src/main/resources/com/example/mariobrosfx/sprites/" +
                "spriteSaltoDerecha.png";
        spriteStillLeft = "src/main/resources/com/example/mariobrosfx/sprites/" +
                "spriteQuietoIzquierda.png";
        spriteStillRight = "src/main/resources/com/example/mariobrosfx/sprites/" +
                "spriteQuietoDerecha.png";
        coordinatesX = new int[2][totalMovements];
        coordinatesY = new int[2][totalMovements];
        coordinatesX[RIGHT] = Configuration.CHARACTER_ANIMATION_RIGHT;
        coordinatesY[RIGHT] = new int[] { 0, 0, 0, 0, 0 };
        coordinatesX[LEFT] = Configuration.CHARACTER_ANIMATION_LEFT;
        coordinatesY[LEFT] = new int[] { 0, 0, 0, 0, 0 };
        totalMovements = 5;

        canJump = true;
        currentGravity = 0;
        currentPlatform = null;
        lastDirection = RIGHT;

        spriteX = 1;
        spriteY = 1;
    }

    /**
     * Method that moves the player to left or right
     * @param movement Direction where the character is going to move
     */
    public void move(int movement)
    {
        int newX = x;
        //If it is on a platform
        if (currentPlatform != null)
        {
            try
            {
                image = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //If it is in the air
        else
        {
            spriteX = 0;
            spriteY = 0;
        }
        if (movement == LEFT)
        {
            lastDirection = LEFT;
            newX -= Configuration.CHARACTER_SPEED;
            //If it is on a platform
            if (currentPlatform != null)
                animate(LEFT);
            //If it is in the air
            else
            {
                try
                {
                    image = new Image(Files.newInputStream(Paths.get(
                            spriteJumpLeft)));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else if (movement == RIGHT)
        {
            newX += Configuration.CHARACTER_SPEED;
            lastDirection = RIGHT;
            //If it is on a platform
            if (currentPlatform != null)
                animate(RIGHT);
            //If it is in the air
            else
            {
                try
                {
                    image = new Image(Files.newInputStream(Paths.get(
                            spriteJumpRight)));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        moveTo(newX, y);
    }

    /**
     * Method that changes the character sprite to a still one
     */
    public void setStillSprite()
    {
        spriteX = 0;
        spriteY = 0;
        if (lastDirection == LEFT)
        {
            try
            {
                image = new Image(Files.newInputStream(Paths.get(
                        spriteStillLeft)));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                image = new Image(Files.newInputStream(Paths.get(
                        spriteStillRight)));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that makes the character jump
     */
    public void jump()
    {
        if (canJump)
        {
            y--;
            currentGravity = Configuration.JUMP_STRENGTH;
            canJump = false;
        }
    }

    /**
     * Method that manages the character gravity
     */
    public void checkGravity()
    {
        //Moves vertically if it's not in a platform
        //and does not hit anything from above
        if (currentPlatform == null && (canFall || currentGravity > 0))
            this.moveTo(this.x, this.y + this.currentGravity);
        //Increases gravity if it has not at the maximum gravity
        //and is not in a platform
        if (this.currentGravity < Configuration.MAX_GRAVITY && currentPlatform
                == null)
            currentGravity += Configuration.GRAVITY;
    }

    /**
     * Method that checks the type of collision between platforms
     * and the character
     * @param platform Platform the character has collided with
     */
    public void checkCollisionType(Platform platform)
    {
        //If collides from above
        if (y < platform.y)
        {
            this.canJump = true;
            this.currentPlatform = platform;
            //This probably will have to be adjusted
            this.y = platform.y - 31;
            this.currentGravity = 0;
            if (this.right || this.left)
            {
                try
                {
                    image = new Image(Files.newInputStream(Paths.get(
                            IMAGE_PATH)));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        //If collides from below
        else
        {
            canFall = false;
        }
    }

    /**
     * Method that changes the canFall parameter
     * @param canFall New value
     */
    public void setCanFall(boolean canFall)
    {
        this.canFall = canFall;
    }

    /**
     * Method that returns the platform where the character is standing
     * @return Platform where the character is standing
     */
    public Platform getCurrentPlatform()
    {
        return currentPlatform;
    }

    /**
     * Method that changes the currentPlatform parameter
     * @param currentPlatform New platform
     */
    public void setCurrentPlatform(Platform currentPlatform)
    {
        this.currentPlatform = currentPlatform;
    }
}