package com.example.mariobrosfx;

import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class that defines a coin that the character can collect
 */
public class Coin extends Sprite
{
    private static final String IMAGE_PATH = "src/main/resources/com/example/" +
            "mariobrosfx/sprites/sprites.png";
    private boolean collected;

    /**
     * Constructor of Coin class
     */
    public Coin()
    {
        super(16, 16);
        spriteX = 109;
        spriteY = 821;
        collected = false;
        try
        {
            image = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that sets if a coin has benn collected
     * @param collected collected state
     */
    public void setCollected(boolean collected)
    {
        this.collected = collected;
    }

    /**
     * Method that returns if a coin has been collected
     * @return if the coin has benn collected
     */
    public boolean isCollected()
    {
        return collected;
    }
}