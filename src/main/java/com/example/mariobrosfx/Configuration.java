package com.example.mariobrosfx;

/**
 * Configuration class with various global parameters
 */
public class Configuration
{
    /**
     * Screen width
     */
    public static final int SCREEN_WIDTH = 640;
    /**
     * Screen height
     */
    public static final int SCREEN_HEIGHT = 600;
    /**
     * Defines the speed at which the animated sprites will animate.
     * The lower the number, the faster the animation
     */
    public static final int FRAME_ANIMATION_RATE = 5;
    /**
     * Character right sprites positions
     */
    public static int[] CHARACTER_ANIMATION_RIGHT = { 80, 96, 112, 128, 144 };
    /**
     * Character left sprites positions
     */
    public static int[] CHARACTER_ANIMATION_LEFT = { 0, 16, 32, 48, 64 };
    /**
     * Initial coordinates of the character
     */
    public static int[] CHARACTER_INITIAL_COORDINATES = {25, 564};
    /**
     * Gravity value
     */
    public static final int GRAVITY = 1;
    /**
     * Character speed
     */
    public static final int CHARACTER_SPEED = 5;
    /**
     * Maximum speed at which the character can fall
     */
    public static final int MAX_GRAVITY = 15;
    /**
     * Character's jump strength
     */
    public static final int JUMP_STRENGTH = -15;
    /**
     * Number of rows of the game map
     */
    public static final int MAP_ROWS = 5; // Number of row in txt map -1
}