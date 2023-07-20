package client.controllers;

import data.Post;
import client.main.Main;
import client.main.Tasks;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class NewPostController implements Initializable
{
    @FXML
    private ImageView IMG;

    @FXML
    private JFXTextArea description;

    private FileChooser chooser;
    private byte[] data;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter
                ("Image Files", "*.jpg", "*.png");

        chooser.getExtensionFilters().add(filter);
    }

    public void chooseAction(ActionEvent actionEvent) throws IOException
    {
        try
        {
            File file = chooser.showOpenDialog(null);

            data = Files.readAllBytes(file.toPath());

            Image image = new Image(new ByteArrayInputStream(data));
            IMG.setImage(image);
        }
        catch (NullPointerException e) {}
    }

    public void postAction(ActionEvent actionEvent) throws IOException
    {
        try
        {
            if(data.length != 0)
            {
                String postDescription = description.getText();
                Post post = new Post(data, postDescription, Main.user.getPosts().size() + 1);
                post.comment(Main.user.getUsername(), postDescription);
                Main.user.getPosts().add(post);

                Main.client.getWriter().writeUTF(Tasks.getNewPostTask(Main.user.getUsername()));
                Main.client.getWriter().writeInt(data.length);
                Main.client.getWriter().write(data);
                Main.client.getWriter().writeUTF(postDescription);

                PagesController.closePage(actionEvent);
                PagesController.openPage("menu");
            }
        }
        catch (NullPointerException e) {}
    }
}
