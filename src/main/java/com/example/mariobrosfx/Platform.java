package com.example.mariobrosfx;

import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Platform extends Sprite
{
    public static final int PLATFORM_WIDTH = 128;
    public static final int PLATFORM_HEIGHT = 16;

    private static final String IMAGE_PATH = "src/main/resources/com/example/mariobrosfx/sprites/platforms_sprite.png";

    public Platform()
    {
        super(PLATFORM_WIDTH, PLATFORM_HEIGHT);
        try
        {
            image = new Image(Files.newInputStream(Paths.get(IMAGE_PATH)));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        spriteX = 0;
        spriteY = 0;
    }
}