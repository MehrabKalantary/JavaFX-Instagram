package client.controllers;

import client.main.NotificationListener;
import data.User;
import client.main.CSS;
import client.main.Main;
import client.main.Tasks;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    private String style = CSS.getTextColor("#ffffff");

    @FXML
    private AnchorPane anchor;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label logLBL;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if(!SplashScreenController.isPlayed())
        {
            try
            {
                SplashScreenController.show(anchor);
            }
            catch (Exception e)
            {
            }
        }

        usernameField.setStyle(style);
        passwordField.setStyle(style);
    }

    public void loginAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException
    {
        String username = usernameField.getText(), password = passwordField.getText();
        Main.client.getWriter().writeUTF(Tasks.getLoginTask(username, password));
        boolean isRight = (boolean) Main.client.getReader().readObject();

        if(isRight)
        {
            Main.user = (User) Main.client.getReader().readObject();

            // start notification thread
            NotificationListener listener = new NotificationListener(Main.client.getReader());
            Main.notification = new Thread(listener);
            Main.notification.start();

            PagesController.closePage(actionEvent);
            PagesController.openPage("menu");
        }
        else
        {
            logLBL.setText("invalid");
        }
    }

    public void signupAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("signup");
    }
}
