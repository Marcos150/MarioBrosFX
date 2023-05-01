package com.example.mariobrosfx;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.*;

public class Game
{
    @FXML
    private Label lblCanGo;
    @FXML
    private Label lblOnPlatform;
    @FXML
    private Label lblplayer;
    @FXML
    private Canvas canvas;
    @FXML
    private Label lblGame;

    private Character player;
    private List<Enemy> enemies;
    private PowBlock pow;
    private final int platformsNumber = 6;
    private Platform[] platforms;
    private boolean secret;
    private int currectGravity;
    private Set<KeyCode> activeKeys;
    private Set<KeyCode> releasedKeys;
    private GraphicsContext gc;
    private AnimationTimer tm = new AnimationTimer() {
        @Override
        public void handle(long timestamp)
        {
            draw();
        }
    };

    public Game()
    {
        currectGravity = 0;
        activeKeys = new HashSet<KeyCode>();
        releasedKeys = new HashSet<KeyCode>();
        canvas = new Canvas();
        platforms = new Platform[platformsNumber];

        try
        {
            player = new Character();
            for (int i = 0; i < platformsNumber; i++)
            {
                platforms[i] = new Platform();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        activeKeys.clear();
        gc = canvas.getGraphicsContext2D();
        tm.start();
    }

    public void lblMouseClick()
    {
        activeKeys.clear();
        gc = canvas.getGraphicsContext2D();
        lblGame.setVisible(false);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Configuration.SCREEN_WIDTH, Configuration.SCREEN_HEIGHT);
        activeKeys = new HashSet<>();
        releasedKeys = new HashSet<>();

        player.moveTo(0, 0);
        generatePlatforms();
    }

    public boolean isColliding(Sprite s1, Platform s2)
    {
        if (s1.collidesWith(s2))
        {
            if (s1 instanceof Character)
            {
                if (player.getY() < s2.getY())
                {
                    player.setOnPlatform(true);
                    player.setCanJump(true);
                }
                else
                    player.setOnPlatform(false);
            }
            return true;
        }
        player.setOnPlatform(false);
        return false;
    }

    public void draw()
    {
        boolean shouldMove = true;
        for (Platform platform : platforms)
        {
            if (shouldMove)
            {
                if (isColliding(player, platform)
                       && !(player.isOnPlatform() && currectGravity < 0)
                        && !(!player.isOnPlatform() && currectGravity > 0))
                {
                    shouldMove = false;
                }
            }
        }
        if (shouldMove)
            player.moveTo(player.getX(), player.getY() + currectGravity);
        if (currectGravity < Configuration.MAX_GRAVITY && !player.isOnPlatform())
            currectGravity += Configuration.GRAVITY;
        drawDebug(shouldMove);
        if (activeKeys.contains(KeyCode.LEFT))
        {
            player.move(Character.LEFT);
        }
        if (activeKeys.contains(KeyCode.RIGHT))
        {
            player.move(Character.RIGHT);
        }
        if (activeKeys.contains(KeyCode.UP))
        {
            if (player.canJump())
            {
                currectGravity = Configuration.JUMP_FORCE;
                player.setCanJump(false);
            }
        }
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Configuration.SCREEN_WIDTH, Configuration.SCREEN_HEIGHT);
        for (Platform platform : platforms)
        {
            platform.draw(gc);
        }
        player.draw(gc);
    }

    public void drawDebug(boolean shouldFall)
    {
        lblplayer.setText(player.getX() + " " + player.getY());
        lblOnPlatform.setText("Is on platform= " + player.isOnPlatform());
        lblCanGo.setText("Can advance: " + shouldFall);
    }

    public void generatePlatforms()
    {
        platforms[0].moveTo(0, 200);
        platforms[1].moveTo(128, 200);
        platforms[2].moveTo(200, 300);
        platforms[3].moveTo(318, 300);
        platforms[4].moveTo(400, 200);
        platforms[5].moveTo(518, 200);
    }

    public void hitPOW()
    {

    }

    public void generateEnemy()
    {

    }

    public void gameTimerTick()
    {

    }

    public void updateHUD()
    {

    }

    public void onKeyPressed(KeyEvent e)
    {
        activeKeys.add(e.getCode());
    }

    public void onKeyReleased(KeyEvent e)
    {
        activeKeys.remove(e.getCode());
        releasedKeys.add(e.getCode());
    }
}