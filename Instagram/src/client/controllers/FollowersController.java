package client.controllers;

import client.main.Main;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FollowersController implements Initializable
{
    @FXML
    private JFXListView<String> list;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> followersList = FXCollections.observableArrayList();

        for (String follower : Main.user.getFollowers())
        {
            followersList.add(follower);
        }

        list.setItems(followersList);
    }

    public void menuAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("menu");
    }
}
