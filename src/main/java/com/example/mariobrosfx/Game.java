package com.example.mariobrosfx;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that manages all the game mechanics and menus
 */
public class Game
{
    @FXML
    private TextField txtFieldName;
    @FXML
    private Button buttonAddRanking;
    @FXML
    private ListView listRanking;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblplayer;
    @FXML
    private Canvas canvas;
    @FXML
    private Label lblGame;

    private Character player;
    private List<Coin> coins;
    private Platform[] platforms;
    private List<RankingRegister> ranking;
    private Set<KeyCode> activeKeys;
    private Set<KeyCode> releasedKeys;
    private GraphicsContext gc;
    private int time;
    private AnimationTimer tm = new AnimationTimer() {
        private long lastUpdate = 0 ;
        @Override
        public void handle(long now)
        {
            if (now - lastUpdate >= 30_000_000)
            {
                time++;
                draw();
                lastUpdate = now;
            }
        }
    };

    /**
     * Constructor of the game class
     */
    public Game()
    {
        File file = new File("ranking.dat");
        if (file.exists())
            ranking = RankingRegister.loadRanking();
        else
        {
            ranking = new ArrayList<>();
            RankingRegister.saveData(ranking);
        }
        canvas = new Canvas();
    }

    /**
     * Method that starts the game when the start label is clicked
     */
    public void start()
    {
        activeKeys = new HashSet<>();
        releasedKeys = new HashSet<>();
        canvas.setWidth(Configuration.SCREEN_WIDTH);
        canvas.setHeight(Configuration.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);

        player = new Character();
        coins = new ArrayList<>();
        //5 is the maximum number of platforms for row
        platforms = new Platform[Configuration.MAP_ROWS * 5];
        for (int i = 0; i < Configuration.MAP_ROWS * 5; i++)
        {
            platforms[i] = new Platform();
        }
        player.moveTo(Configuration.CHARACTER_INITIAL_COORDINATES[0],
                Configuration.CHARACTER_INITIAL_COORDINATES[1]);
        generatePlatforms();
        generateCoins();
        buttonAddRanking.setVisible(false);
        listRanking.setVisible(false);
        txtFieldName.setVisible(false);
        lblGame.setVisible(false);
        lblTime.setVisible(true);
        time = 0;
        tm.start();
    }

    /**
     * Method that draws all the drawable elements
     */
    public void draw()
    {
        checkCollision();
        player.checkGravity();
        manageKeys();
        gc.fillRect(0, 0, Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT);
        for (Platform platform : platforms)
        {
            platform.draw(gc);
        }
        player.draw(gc);
        updateHUD();
        checkCoinsAndWin();
    }

    /**
     * Method that checks if any keys have been hit
     */
    public void manageKeys()
    {
        if (activeKeys.contains(KeyCode.LEFT) &&
                !activeKeys.contains(KeyCode.RIGHT))
            player.move(Character.LEFT);
        else if (activeKeys.contains(KeyCode.RIGHT) &&
                !activeKeys.contains(KeyCode.LEFT))
            player.move(Character.RIGHT);
        else
            player.setStillSprite();
        if (activeKeys.contains(KeyCode.UP))
            player.jump();
    }

    /**
     * Method that checks which coins have been collected and checks if
     * the game has to finish
     */
    public void checkCoinsAndWin()
    {
        boolean win = true;
        for (Coin c : coins)
        {
            if (!c.isCollected())
            {
                c.draw(gc);
                win = false;
            }
        }
        if (win && lblTime.visibleProperty().get()) //If game has started
            gameOver();
    }

    /**
     * Method that places all the platforms according to the map.txt file
     */
    public void generatePlatforms()
    {
        int counter = 0;
        try
        {
            File mapFile = new File("src/main/resources/com/example/" +
                    "mariobrosfx/map.txt");
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
                //-1 so that the last line is barely visible
                y += Configuration.SCREEN_HEIGHT/Configuration.MAP_ROWS - 1;
            }
            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that places all the coins according to the coins.txt file
     */
    public void generateCoins()
    {
        coins = new ArrayList<>();
        try
        {
            File coinFile = new File("src/main/resources/com/example/" +
                    "mariobrosfx/coins.txt");
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

    /**
     * Method that checks the collision between the player
     * and the rest of the elements
     */
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
                c.setCollected(true);
        }
    }

    /**
     * Method that updates the HUD
     */
    public void updateHUD()
    {
        lblTime.setText("Time: " + time);
    }

    /**
     * Method that shows the game over screen when the game finishes
     */
    public void gameOver()
    {
        tm.stop();
        lblGame.setVisible(true);
        lblGame.setTextFill(Color.BLACK);
        lblplayer.setVisible(true);
        lblplayer.setTextFill(Color.BLACK);
        lblplayer.setText("Final time: " + time);
        lblGame.setText("Play again");
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT);
        listRanking.setVisible(true);
        txtFieldName.setVisible(true);
        buttonAddRanking.setVisible(true);
        ranking.sort(Comparator.comparingInt(RankingRegister::getTime));
        listRanking.getItems().setAll(ranking);
    }

    /**
     * Method that adds a new register to the ranking
     */
    public void addRanking()
    {
        RankingRegister register = new RankingRegister(time,
                txtFieldName.getText());
        ranking.add(register);
        RankingRegister.saveData(ranking);
        ranking.sort(Comparator.comparingInt(RankingRegister::getTime));
        listRanking.getItems().setAll(ranking);
        buttonAddRanking.setVisible(false);
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