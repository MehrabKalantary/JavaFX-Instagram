package client.controllers;

import client.main.Main;
import client.main.Tasks;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable
{
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public void newPostAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("newPost");
    }

    public void exitAction(ActionEvent actionEvent) throws IOException
    {
        Main.client.getWriter().writeUTF(Tasks.getExitTask(Main.user.getUsername()));

        Main.client.getReader().close();
        Main.client.getWriter().close();
        Main.client.getSocket().close();

        System.exit(0);
    }


    public void profileAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("profile");
    }

    public void postsAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("my posts list");
    }

    public void directAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("direct list");
    }
}