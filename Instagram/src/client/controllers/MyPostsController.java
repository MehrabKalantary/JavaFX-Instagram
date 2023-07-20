package client.controllers;

import client.main.Main;
import com.jfoenix.controls.JFXListView;
import data.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyPostsController implements Initializable
{
    @FXML
    private ImageView IMG;
    @FXML
    private Label infoLBL;
    @FXML
    private JFXListView<String> comments;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        int postNum = Integer.parseInt(MyPostsListController.getSelected().substring(0, 1));

        for (Post post : Main.user.getPosts())
        {
            if(post.getPostNumber() == postNum)
            {
                IMG.setImage(new Image(new ByteArrayInputStream(post.getPhoto())));

                infoLBL.setText("likes : " + post.getLikes() + "\n" +
                "date : " + post.getDate() + "\n" + "time : " + post.getTime());

                ObservableList<String> list = FXCollections.observableArrayList();

                for (String username : post.getComments().keySet())
                {
                    String comment = username + " : " + post.getComments().get(username);
                    list.add(comment);
                }

                comments.setItems(list);
                break;
            }
        }
    }

    public void close(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("my posts list");
    }
}
