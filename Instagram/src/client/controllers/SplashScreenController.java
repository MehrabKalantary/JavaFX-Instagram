package client.controllers;

import client.main.SplashScreenPlayer;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable
{
    private static boolean isFinished = false;

    public static boolean isPlayed()
    {
        return isFinished;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public static void show(AnchorPane pane) throws IOException
    {
        isFinished = true;

        String splashScreen_URL = "../pages/splashScreen.fxml";
        String mainPage_URL = "../pages/login.fxml";

        SplashScreenPlayer player = new SplashScreenPlayer(pane, splashScreen_URL, mainPage_URL);
        player.play();
    }
}