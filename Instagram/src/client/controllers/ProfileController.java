package client.controllers;

import client.main.Main;
import client.main.Tasks;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ProfileController implements Initializable
{
    @FXML
    private ImageView profileIMG;
    @FXML
    private Label nameLBL;

    private FileChooser chooser;
    private byte[] data;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        nameLBL.setText(Main.user.getUsername());

        chooser = new FileChooser();

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter
                ("Image Files", "*.jpg", "*.png");

        chooser.getExtensionFilters().add(filter);

        try
        {
            profileIMG.setImage(new Image(new ByteArrayInputStream(Main.user.getProfile_photo())));
        }
        catch (NullPointerException e) {}
    }

    public void menuAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("menu");
    }

    public void changeBTN(ActionEvent actionEvent) throws IOException
    {
        try
        {
            File file = chooser.showOpenDialog(null);

            data = Files.readAllBytes(file.toPath());
            Main.user.setProfile(data);

            Main.client.getWriter().writeUTF(Tasks.getNewProfileTask(Main.user.getUsername()));
            Main.client.getWriter().writeInt(data.length);
            Main.client.getWriter().write(data);

            Image image = new Image(new ByteArrayInputStream(data));
            profileIMG.setImage(image);
        }
        catch (NullPointerException e) {}
    }

    public void followersAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("followers");
    }

    public void followingAction(ActionEvent actionEvent) throws IOException
    {
        PagesController.closePage(actionEvent);
        PagesController.openPage("following");
    }
}
