package com.example.mariobrosfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class that initializes the game
 */
public class Main extends Application
{
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "game-scene.fxml"));
        scene = fxmlLoader.load();
        stage.setTitle("Mario Bros");
        stage.setScene(scene);
        stage.setWidth(Configuration.SCREEN_WIDTH);
        //For some reason, this method sizes the screen with
        //-28 pixels than specified on Mac an -35 on Windows
        String os = System.getProperty("os.name");
        if (os.startsWith("Mac"))
            stage.setHeight(Configuration.SCREEN_HEIGHT + 28);
        if (os.startsWith("Windows"))
            stage.setHeight(Configuration.SCREEN_HEIGHT + 35);

        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}