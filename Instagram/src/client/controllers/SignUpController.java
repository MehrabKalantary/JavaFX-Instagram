package client.controllers;

import client.main.CSS;
import client.main.Main;
import client.main.Tasks;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable
{
    private String style = CSS.getTextColor("#ffffff");

    @FXML
    private Label logLBL;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        usernameField.setStyle(style);
        passwordField.setStyle(style);
    }

    public void signupAction(ActionEvent actionEvent) throws IOException, InterruptedException, ClassNotFoundException
    {
        boolean flag = true;

        String userName = usernameField.getText();
        String pass = passwordField.getText();

        Main.client.getWriter().writeUTF(Tasks.getCheckUsernameTask(userName));

        boolean exists = (boolean) Main.client.getReader().readObject();

        if(exists)
        {
            logLBL.setText("this username is used");
            flag = false;
        }

        if(!userName.matches("\\w*") || userName.length() < 4)
        {
            logLBL.setText("invalid username");
            flag = false;
        }

        if(userName.length() > 20)
        {
            logLBL.setText("username is too long");
            flag = false;
        }

        if(pass.contains(" "))
        {
            logLBL.setText("invalid password");
            flag = false;
        }

        if(pass.length() < 8)
        {
            logLBL.setText("password is too short");
            flag = false;
        }

        if(flag)
        {
            Main.client.getWriter().writeUTF(Tasks.getSignUpTask(userName, pass));

            Thread.sleep(500);

            PagesController.closePage(actionEvent);
            PagesController.openPage("login");
        }
    }
}
