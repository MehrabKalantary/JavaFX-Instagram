package client.controllers;

import client.main.Main;
import client.main.NotificationListener;
import client.main.Tasks;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FollowingController implements Initializable
{
    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXListView<String> list;

    @FXML
    private Button unfollowBTN, profileBTN;
    private static String selected;

    public static String getSelected()
    {
        return selected;
    }

    private void print()
    {
        list.setItems(FXCollections.observableArrayList(Main.user.getFollowings()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        print();
    }

    public void menuAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("menu");
    }

    public void followAction(ActionEvent actionEvent) throws IOException
    {
        String name = searchField.getText();

        if(!name.equals(Main.user.getUsername()) && !Main.user.getFollowings().contains(name))
        {
            Main.client.getWriter().writeUTF(Tasks.getFollowTask(name));

            boolean exist = (boolean) NotificationListener.getMessage();

            if(exist)
            {
                Main.user.getFollowings().add(name);
                print();
            }
        }
    }

    public void listClicked(MouseEvent mouseEvent)
    {
        try
        {
            selected = list.getSelectionModel().getSelectedItem();

            if(selected.length() != 0)
            {
                unfollowBTN.setDisable(false);
                profileBTN.setDisable(false);
            }
        }
        catch (NullPointerException e) {}
    }

    public void unfollowAction(ActionEvent actionEvent) throws IOException
    {
        Main.user.getFollowings().remove(selected);
        print();

        Main.client.getWriter().writeUTF(Tasks.getUnfollowTask(selected));
    }

    public void profileAction(ActionEvent actionEvent) throws IOException
    {
        Main.client.getWriter().writeUTF(Tasks.getProfileTask(selected));

        byte[] photo = (byte[]) NotificationListener.getMessage();

        UserProfileController.setPhoto(photo);
        UserProfileController.setUsername(selected);

        PagesController.closePage(actionEvent);
        PagesController.openPage("user profile");
    }
}
