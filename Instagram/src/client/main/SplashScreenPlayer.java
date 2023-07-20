package client.main;

import client.controllers.SplashScreenController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreenPlayer
{
    private AnchorPane mainPane;
    private static final double time_second = 4, fromValue = 0, toValue = 1;
    private static final int cycleCount = 1;
    private FadeTransition fadeIn, fadeOut;
    private String splashScreen_URL, mainPage_URL;

    public SplashScreenPlayer(AnchorPane mainPane, String splashScreen_URL, String mainPage_URL)
    {
        this.mainPane = mainPane;
        this.splashScreen_URL = splashScreen_URL;
        this.mainPage_URL = mainPage_URL;
    }

    public void play() throws IOException
    {
        setup();

        fadeIn.play();

        fadeIn.setOnFinished((event ->
        {
            fadeOut.play();
        }));


        fadeOut.setOnFinished((event ->
        {
            try
            {
                AnchorPane anchorPane = FXMLLoader.load(SplashScreenController.class.
                        getResource(mainPage_URL));

                mainPane.getChildren().setAll(anchorPane);
            }
            catch (IOException e)
            {
            }

        }));
    }


    /**
     * set fade in and fade out options
     * @throws IOException
     */
    private void setup() throws IOException
    {
        AnchorPane anchorPane = FXMLLoader.load(SplashScreenController.class.getResource(splashScreen_URL));
        mainPane.getChildren().setAll(anchorPane);

        // fade in
        fadeIn = new FadeTransition(Duration.seconds(time_second), anchorPane);
        fadeIn.setFromValue(fromValue);
        fadeIn.setToValue(toValue);
        fadeIn.setCycleCount(cycleCount);

        // fade out
        fadeOut = new FadeTransition(Duration.seconds(time_second), anchorPane);
        fadeOut.setFromValue(toValue);
        fadeOut.setToValue(fromValue);
        fadeOut.setCycleCount(cycleCount);
    }
}
