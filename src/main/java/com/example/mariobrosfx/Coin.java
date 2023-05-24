package com.example.mariobrosfx;

import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Coin extends Sprite
{
    private static final String IMAGE_PATH = "src/main/resources/com/example/mariobrosfx/sprites/sprites.png";
    private boolean collected;

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

    public void setCollected(boolean collected)
    {
        this.collected = collected;
    }

    public boolean isCollected()
    {
        return collected;
    }
}