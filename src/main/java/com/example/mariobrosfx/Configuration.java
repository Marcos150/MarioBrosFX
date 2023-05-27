package com.example.mariobrosfx;

public class Configuration
{
    //TODO find a proper screen size
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 600;
    //TODO change this when I have the sprites sheets
    public static final int FRAME_ANIMATION_RATE = 5;
    public static int[] CHARACTER_ANIMATION_RIGHT = { 80, 96, 112, 128, 144 };
    public static int[] CHARACTER_ANIMATION_LEFT = { 0, 16, 32, 48, 64 };
    public static int[] CHARACTER_INITIAL_COORDINATES = {250, 32};
    public static final int INITIAL_LIVES = 3;
    public static final int POW_BLOCK_USAGES = 3;
    public static final int INITIAL_ENEMY_SPEED = 5;
    public static final int GRAVITY = 1;
    public static final int CHARACTER_SPEED = 5;
    public static final int MAX_GRAVITY = 15;
    public static final int JUMP_FORCE = -15;
    public static final int MAP_ROWS = 5; // Number of row in txt map -1
    public static final int COIN_POINTS = 300;

    //TODO fix secret code
    public static final String SECRET_CODE = "Up, Up, Down, Down, Left, Right, Left, Right, B, A";
}