package client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController implements Initializable
{
    @FXML
    private Label notificationLBL;

    private Stage stage;
    private static String message;


    public static void setMessage(String message)
    {
        NotificationController.message = message;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        stage = PagesController.getStage();
        notificationLBL.setText(message);
    }

    public void closeAction(ActionEvent actionEvent)
    {
        stage.close();
    }
}
