package client.main;

import client.controllers.DirectController;
import client.controllers.PagesController;
import data.Direct;
import data.Post;
import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class NotificationListener implements Runnable
{
    private ObjectInputStream reader;
    private String message;
    private JSONObject mapper;
    private static Object msg;
    private static CountDownLatch counter;

    public NotificationListener(ObjectInputStream reader)
    {
        this.reader = reader;
        counter = new CountDownLatch(1);
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                message = (String) reader.readObject();
                mapper = new JSONObject(message);
                handle();
            }
            catch (Exception e)
            {
            }
        }
    }

    private void handle() throws JSONException, IOException, ClassNotFoundException
    {
        String task = mapper.getString("task");

        switch (task)
        {
            case "read" :
            {
                msg = reader.readObject();
                counter.countDown();
            }
            break;

            case "follow" : follow();
            break;
            case "unfollow" : unfollow();
            break;
            case "like" : like();
            break;
            case "comment" : comment();
            break;
            case "new direct" : newDirect();
            break;
            case "new message" : newMessage();
            break;
        }
    }

    private void newMessage() throws JSONException
    {
        String from = mapper.getString("from"), message = mapper.getString("message");

        for (Direct direct : Main.user.getDirects())
        {
            if(direct.getTo_user().equals(from))
            {
                direct.newMessage(from, message);
            }
        }

        Platform.runLater(() ->
        {
            try
            {
                PagesController.notification("new message from " + from);
                DirectController.messages.add(from + " : " + message);
            }
            catch (IOException e) {}
        });
    }

    private void newDirect() throws JSONException
    {
        String from = mapper.getString("from");

        Direct direct = new Direct(from, Main.user.getUsername());
        Main.user.getDirects().add(direct);
    }

    private void comment() throws JSONException
    {
        String username = mapper.getString("username"), comment = mapper.getString("comment");
        int postNumber = mapper.getInt("post number");

        for (Post post : Main.user.getPosts())
        {
            if(post.getPostNumber() == postNumber)
            {
                post.comment(username, comment);
            }
        }

        Platform.runLater(() ->
        {
            try
            {
                PagesController.notification("new comment from " + username);
            }
            catch (IOException e) {}
        });
    }

    private void like() throws JSONException
    {
        String liker = mapper.getString("liker");
        int postNumber = mapper.getInt("post number");

        for (Post post : Main.user.getPosts())
        {
            if(post.getPostNumber() == postNumber)
            {
                if(!post.getLiking_users().contains(liker))
                {
                    Platform.runLater(() ->
                    {
                        try
                        {
                            PagesController.notification(liker + " just liked your post");
                        }
                        catch (IOException e) {}
                    });
                }

                post.like(liker);
            }
        }
    }

    private void unfollow() throws JSONException
    {
        String unfollower = mapper.getString("unfollower");
        Main.user.getFollowers().remove(unfollower);
    }

    private void follow() throws JSONException
    {
        String follower = mapper.getString("follower");
        Main.user.getFollowers().add(follower);

        Platform.runLater(() ->
        {
            try
            {
                PagesController.notification(follower + " just followed you");
            }
            catch (IOException e) {}
        });
    }

    public static Object getMessage()
    {
        try
        {
            // wait to receive object from server
            counter.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        counter = new CountDownLatch(1);
        return msg;
    }
}
