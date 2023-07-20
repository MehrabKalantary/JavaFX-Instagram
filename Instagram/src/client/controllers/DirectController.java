package client.controllers;

import client.main.Main;
import client.main.Tasks;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import data.Direct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DirectController implements Initializable
{
    @FXML
    private Label nameLBL;
    private static String name;

    public static void setName(String name)
    {
        DirectController.name = name;
    }

    @FXML
    private JFXTextArea textField;
    @FXML
    private JFXListView<String> list;
    private static Direct direct;
    public static ObservableList<String> messages;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nameLBL.setText(name);

        for (Direct direct : Main.user.getDirects())
        {
            if(direct.getTo_user().equals(name))
            {
                DirectController.direct = direct;
                break;
            }
        }
        print();
    }

    public void print()
    {
        messages = FXCollections.observableArrayList();

        for (String message : direct.getMessages())
        {
            messages.add(message);
        }

        list.setItems(messages);
    }


    public void back(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("direct list");
    }

    public void sendAction(ActionEvent actionEvent) throws IOException
    {
        String message = textField.getText();

        if(message.trim().length() != 0)
        {
            direct.newMessage(Main.user.getUsername(), message);
            Main.client.getWriter().writeUTF(Tasks.getNewMessageTask(Main.user.getUsername(), name, message));

            print();
        }
        textField.setText("");
    }
}
