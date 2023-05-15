package com.example.mariobrosfx;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Character extends Sprite
{
    private int lives;
    private int points;
    public static final int CHARACTER_WIDTH = 16;
    public static final int CHARACTER_HEIGHT = 32;
    private static final String IMAGE_PATH = "src/main/resources/com/example/mariobrosfx/sprites/sprites.png";
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private boolean isOnPlatform;
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
        isOnPlatform = false;
        canJump = true;
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

    public void respawn()
    {

    }

    public boolean canJump()
    {
        return canJump;
    }

    public void setCanJump(boolean canJump)
    {
        this.canJump = canJump;
    }

    public boolean isOnPlatform()
    {
        return isOnPlatform;
    }

    public void setOnPlatform(boolean onPlatform)
    {
        isOnPlatform = onPlatform;
    }
}