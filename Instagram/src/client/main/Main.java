package client.main;

import client.controllers.PagesController;
import javafx.application.Application;
import javafx.stage.Stage;
import data.User;
import java.io.IOException;

public class Main extends Application
{
    public static Client client;
    public static Thread notification;

    // user who logins
    public static User user;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        client = new Client();
        client.connect();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        PagesController.openPage("login");
    }
}
