package com.example.mariobrosfx;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Character extends AnimatedSprite
{
    private int lives;
    private int points;
    private int currentGravity;
    public static final int CHARACTER_WIDTH = 16;
    public static final int CHARACTER_HEIGHT = 32;
    private static final String IMAGE_PATH = "src/main/resources/com/example/mariobrosfx/sprites/sprites.png";
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    //Platform where the player is standing
    private Platform currentPlatform;
    private boolean canFall;
    private boolean canJump;

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

        lives = Configuration.INITIAL_LIVES;
        canJump = true;
        currentGravity = 0;
        points = 0;
        currentPlatform = null;

        spriteX = 1;
        spriteY = 1;
    }

    public void move(int movement)
    {
        int newX = x;
        if (movement == LEFT)
            newX -= Configuration.CHARACTER_SPEED;
        if (movement == RIGHT)
            newX += Configuration.CHARACTER_SPEED;
        moveTo(newX, y);
    }

    public void jump()
    {
        if (canJump)
        {
            y = y-1;
            currentGravity = Configuration.JUMP_FORCE;
            canJump = false;
        }
    }

    public void checkGravity()
    {
        //Moves vertically if it's not in a platform
        //and does not hit anything from above
        if (currentPlatform == null && (canFall || currentGravity > 0))
            this.moveTo(this.x, this.y + this.currentGravity);
        //Increases gravity if it has not at the maximum gravity
        //and is not in a platform
        if (this.currentGravity < Configuration.MAX_GRAVITY && currentPlatform == null)
            currentGravity += Configuration.GRAVITY;
    }

    public void respawn()
    {

    }

    public int checkCollisionType(Platform p)
    {
        //If collides from above
        if (y < p.y)
        {
            this.canJump = true;
            this.currentPlatform = p;
            //This probably will have to be adjusted
            this.y = p.y - 31;
            this.currentGravity = 0;
            if (this.right || this.left)
            {
                try
                {
                    image = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return 0;
        }
        //If collides from below
        else
        {
            canFall = false;
            return 1;
        }
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getPoints()
    {
        return points;
    }

    public void setCanFall(boolean canFall)
    {
        this.canFall = canFall;
    }

    public Platform getCurrentPlatform()
    {
        return currentPlatform;
    }

    public void setCurrentPlatform(Platform currentPlatform)
    {
        this.currentPlatform = currentPlatform;
    }
}