package client.controllers;


import client.main.Main;
import client.main.NotificationListener;
import client.main.Tasks;
import data.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable
{
    private static String username;
    private static byte[] photo;

    @FXML
    private ImageView profileIMG;
    @FXML
    private Label nameLBL;

    public static void setUsername(String username)
    {
        UserProfileController.username = username;
    }

    public static void setPhoto(byte[] photo)
    {
        UserProfileController.photo = photo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nameLBL.setText(username);
         try
         {
             Image image = new Image(new ByteArrayInputStream(photo));
             profileIMG.setImage(image);
         }
         catch (NullPointerException e) {}
    }

    public void backAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("following");
    }

    public void postsAction(ActionEvent actionEvent) throws IOException
    {
        Main.client.getWriter().writeUTF(Tasks.getPostsTask(username));

        ArrayList<Post> posts = (ArrayList<Post>) NotificationListener.getMessage();

        UserPostsListController.setPosts(posts);
        PagesController.closePage(actionEvent);
        PagesController.openPage("user posts list");
    }
}
