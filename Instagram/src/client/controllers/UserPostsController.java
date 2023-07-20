package client.controllers;

import client.main.Main;
import client.main.Tasks;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
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

public class UserPostsController implements Initializable
{
    @FXML
    private ImageView IMG;
    @FXML
    private Label infoLBL;
    @FXML
    private JFXListView<String> comments;
    private static int postNumber;

    @FXML
    private JFXTextField commentField;

    private void serComments(Post post)
    {
        ObservableList<String> list = FXCollections.observableArrayList();

        for (String username : post.getComments().keySet())
        {
            String comment = username + " : " + post.getComments().get(username);
            list.add(comment);
        }

        comments.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        postNumber = Integer.parseInt(UserPostsListController.getSelected().substring(0, 1));

        for (Post post : UserPostsListController.getPosts())
        {
            if(post.getPostNumber() == postNumber)
            {
                IMG.setImage(new Image(new ByteArrayInputStream(post.getPhoto())));

                infoLBL.setText("likes : " + post.getLikes() + "\n" +
                        "date : " + post.getDate() + "\n" + "time : " + post.getTime());

                serComments(post);
                break;
            }
        }
    }

    public void close(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("user posts list");
    }

    public void likeAction(ActionEvent actionEvent) throws IOException
    {
        for (Post post : UserPostsListController.getPosts())
        {
            if(post.getPostNumber() == postNumber)
            {
                post.like(Main.user.getUsername());
                infoLBL.setText("likes : " + post.getLikes() + "\n" +
                        "date : " + post.getDate() + "\n" + "time : " + post.getTime());

                Main.client.getWriter().writeUTF(Tasks.getLikeTask(Main.user.getUsername(),
                        FollowingController.getSelected(), String.valueOf(postNumber)));
            }
        }
    }

    public void commentAction(ActionEvent actionEvent) throws IOException
    {
        String comment = commentField.getText();

        if(comment.trim().length() != 0)
        {
            for (Post post : UserPostsListController.getPosts())
            {
                if(post.getPostNumber() == postNumber)
                {
                    post.comment(Main.user.getUsername(), comment);
                    serComments(post);

                    Main.client.getWriter().writeUTF(Tasks.getCommentTask(Main.user.getUsername(),
                            FollowingController.getSelected(), String.valueOf(postNumber), comment));
                }
            }
        }
    }
}
