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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game
{
    @FXML
    private Label lblPoints;
    @FXML
    private Label lblOnPlatform;
    @FXML
    private Label lblplayer;
    @FXML
    private Canvas canvas;
    @FXML
    private Label lblGame;

    private Character player;
    private List<Coin> coins;
    private Platform[] platforms;
    private boolean secret;
    private Set<KeyCode> activeKeys;
    private Set<KeyCode> releasedKeys;
    private GraphicsContext gc;
    private AnimationTimer tm = new AnimationTimer() {
        private long lastUpdate = 0 ;
        @Override
        public void handle(long now)
        {
            if (now - lastUpdate >= 30_000_000)
            {
                draw();
                lastUpdate = now;
            }
        }
    };

    public Game()
    {
        activeKeys = new HashSet<KeyCode>();
        releasedKeys = new HashSet<KeyCode>();
        canvas = new Canvas();
        //5 is the maximum number of platforms for row
        platforms = new Platform[Configuration.MAP_ROWS * 5];
        coins = new ArrayList<>();
        player = new Character();

        try
        {
            for (int i = 0; i < Configuration.MAP_ROWS * 5; i++)
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
        canvas.setWidth(Configuration.SCREEN_WIDTH);
        canvas.setHeight(Configuration.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        lblGame.setTextFill(Color.WHITE);
        lblGame.setText("Refresh map");
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Configuration.SCREEN_WIDTH, Configuration.SCREEN_HEIGHT);
        activeKeys = new HashSet<>();
        releasedKeys = new HashSet<>();

        player.moveTo(Configuration.CHARACTER_INITIAL_COORDINATES[0],
                Configuration.CHARACTER_INITIAL_COORDINATES[1]);
        generatePlatforms();
        generateCoins();
    }

    public void draw()
    {
        checkCollision();
        player.checkGravity();
        drawDebug();
        manageKeys();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Configuration.SCREEN_WIDTH, Configuration.SCREEN_HEIGHT);
        for (Platform platform : platforms)
        {
            platform.draw(gc);
        }
        for (Coin c : coins)
        {
            if (!c.isCollected())
                c.draw(gc);
        }
        player.draw(gc);
        updateHUD();
    }

    public void drawDebug()
    {
        lblplayer.setText(player.getX() + " " + player.getY());
        lblOnPlatform.setText("Current platform= " + player.getCurrentPlatform());
    }

    public void manageKeys()
    {
        if (activeKeys.contains(KeyCode.LEFT) && !activeKeys.contains(KeyCode.RIGHT))
            player.move(Character.LEFT);
        else if (activeKeys.contains(KeyCode.RIGHT) && !activeKeys.contains(KeyCode.LEFT))
            player.move(Character.RIGHT);
        else
            player.setStillSprite();
        if (activeKeys.contains(KeyCode.UP))
            player.jump();
    }

    public void generatePlatforms()
    {
        int counter = 0;
        try
        {
            File mapFile = new File("src/main/resources/com/example/mariobrosfx/map.txt");
            Scanner myReader = new Scanner(mapFile);
            int y = 0;
            while (myReader.hasNextLine())
            {
                String row = myReader.nextLine();
                int x = 0;
                for (char c : row.toCharArray())
                {
                    if (c == 'x')
                    {
                        platforms[counter].moveTo(x, y);
                        counter++;
                    }
                    x += 128;
                }
                y += Configuration.SCREEN_HEIGHT/Configuration.MAP_ROWS - 1; //-1 so that the last line is barely visible
            }
            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void generateCoins()
    {
        coins = new ArrayList<>();
        try
        {
            File coinFile = new File("src/main/resources/com/example/mariobrosfx/coins.txt");
            Scanner myReader = new Scanner(coinFile);
            int y = 40;

            while (myReader.hasNextLine())
            {
                String row = myReader.nextLine();
                int x = 0;

                for (char c : row.toCharArray())
                {
                    if (c == 'X')
                    {
                        Coin coin = new Coin();
                        coin.moveTo(x, y);
                        coins.add(coin);
                    }
                    x += 16;
                }
                y += Configuration.SCREEN_HEIGHT/Configuration.MAP_ROWS;
            }
            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void checkCollision()
    {
        //Platforms
        if (player.getCurrentPlatform() == null)
        {
            player.setCanFall(true);
            for (Platform p : platforms)
            {
                if (player.collidesWith(p))
                {
                    player.checkCollisionType(p);
                }
            }
        }
        //Checks if player is still on the platform
        else
        {
            if (!player.collidesWith((player.getCurrentPlatform())))
                player.setCurrentPlatform(null);
        }

        //Checks if player has reached a screen border and takes it to the other
        if (player.getX() >= Configuration.SCREEN_WIDTH - 15)
            player.moveTo(1, player.getY());
        else if (player.getX() <= 0)
            player.moveTo(Configuration.SCREEN_WIDTH - 16, player.getY());

        //Coins
        for (Coin c : coins)
        {
            if (player.collidesWith(c) && !c.isCollected())
            {
                c.setCollected(true);
                player.setPoints(player.getPoints() + Configuration.COIN_POINTS);
            }
        }
    }

    public void updateHUD()
    {
        lblPoints.setText("Points: " + player.getPoints());
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