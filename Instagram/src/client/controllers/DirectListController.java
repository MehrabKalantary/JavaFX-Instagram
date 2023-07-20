package client.controllers;

import client.main.Main;
import client.main.NotificationListener;
import client.main.Tasks;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import data.Direct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DirectListController implements Initializable
{
    @FXML
    private JFXListView<String> list;
    @FXML
    private JFXButton msgBTN;
    @FXML
    private JFXTextField searchField;
    private static String selected;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        print();
    }

    public void listClicked(MouseEvent mouseEvent)
    {
        try
        {
            selected = list.getSelectionModel().getSelectedItem();

            if(selected.length() != 0)
            {
                msgBTN.setDisable(false);
            }
        }
        catch (NullPointerException e) {}
    }

    public void sendAction(ActionEvent actionEvent) throws IOException
    {
        String username = searchField.getText();

        if(Main.user.getDirects().stream().filter(direct -> direct.getTo_user().equals(username)).count() == 0 &&
                Main.user.getFollowings().contains(username))
        {
            Direct direct = new Direct(username, Main.user.getUsername());
            Main.user.getDirects().add(direct);
            Main.client.getWriter().writeUTF(Tasks.getNewDirectTask(Main.user.getUsername(), username));
        }

        print();
    }

    private void print()
    {
        ObservableList<String> names = FXCollections.observableArrayList();

        for (Direct direct : Main.user.getDirects())
        {
            names.add(direct.getTo_user());
        }

        list.setItems(names);
    }

    public void menuAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("menu");
    }

    public void msgAction(ActionEvent actionEvent) throws IOException
    {
        DirectController.setName(selected);
        PagesController.closePage(actionEvent);
        PagesController.openPage("direct");
    }
}
