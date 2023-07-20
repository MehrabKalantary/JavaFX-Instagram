package client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import data.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserPostsListController implements Initializable
{
    @FXML
    private JFXListView<String> postsList;
    private static String selected;

    @FXML
    private JFXButton BTN;

    public static String getSelected()
    {
        return selected;
    }

    private static List<Post> posts;

    public static List<Post> getPosts()
    {
        return posts;
    }

    public static void setPosts(List<Post> posts)
    {
        UserPostsListController.posts = posts;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> list = FXCollections.observableArrayList();

        for (Post post : posts)
        {
            String tmp;

            if(post.getDescription().length() == 0)
            {
                tmp = post.getPostNumber() + "_" + "no description";
            }
            else
            {
                tmp = post.getPostNumber() + "_" + post.getDescription();
            }

            list.add(tmp);
        }

        postsList.setItems(list);
    }

    public void select(MouseEvent mouseEvent)
    {
        try
        {
            selected = postsList.getSelectionModel().getSelectedItem();

            if(selected.length() != 0)
            {
                BTN.setDisable(false);
            }
        }
        catch (NullPointerException e) {}
    }

    public void close(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("user profile");
    }

    public void seeAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("user posts");
    }
}
