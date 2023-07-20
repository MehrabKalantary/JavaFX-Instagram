package server.main;

import data.Direct;
import data.Post;
import javafx.concurrent.Task;
import org.json.JSONException;
import org.json.JSONObject;
import data.User;
import server.database.DataBase;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * this class handles client's message
 */

public class TaskHandler
{
    private JSONObject mapper;
    private ObjectOutputStream writer;
    private DataInputStream reader;

    public TaskHandler(JSONObject mapper, ObjectOutputStream writer, DataInputStream reader)
    {
        this.mapper = mapper;
        this.writer = writer;
        this.reader = reader;
    }

    public void handle() throws JSONException, IOException
    {
        switch (mapper.getString("task"))
        {
            case "check username" : checkUsername();
            break;
            case "signup" : signUp();
            break;
            case "login" : login();
            break;
            case "exit" : exit();
            break;
            case "new post" : newPost();
            break;
            case "new profile" : newProfile();
            break;
            case "new direct" : newDirect();
            break;
            case "new message" : newMessage();
            break;

            // tasks with sending data from server
            case "follow" : follow();
            break;
            case "unfollow" : unfollow();
            break;
            case "profile" : profile();
            break;
            case "get posts" : getPosts();
            break;
            case "like" : like();
            break;
            case "comment" : comment();
            break;
        }
    }

    private void newMessage() throws IOException, JSONException
    {
        String from = mapper.getString("from"), to = mapper.getString("to"),
                message = mapper.getString("message");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(from))
            {
                for (Direct direct : user.getDirects())
                {
                    if(direct.getTo_user().equals(to))
                    {
                        direct.newMessage(from, message);
                    }
                }
            }

            if(user.getUsername().equals(to))
            {
                for (Direct direct : user.getDirects())
                {
                    if(direct.getTo_user().equals(from))
                    {
                        direct.newMessage(from, message);
                    }
                }
            }
        }

        DataBase.writeData();

        try
        {
            Main.onlineUsers_data.get(to).getWriter().writeObject(Tasks.getNewMessageTask(from, message));
        }
        catch (NullPointerException e) {}
    }

    private void newDirect() throws IOException, JSONException
    {
        String from = mapper.getString("from"), to = mapper.getString("to");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(from))
            {
                Direct direct = new Direct(to, from);
                user.getDirects().add(direct);
            }
            if(user.getUsername().equals(to))
            {
                Direct direct = new Direct(from, to);
                user.getDirects().add(direct);
            }
        }
        DataBase.writeData();

        try
        {
            Main.onlineUsers_data.get(to).getWriter().writeObject(Tasks.getNewDirectTask(from));
        }
        catch (NullPointerException e) {}
    }

    private void comment() throws IOException, JSONException
    {
        String commenting_user = mapper.getString("username"), commented_user = mapper.getString("commented");
        int postNumber = mapper.getInt("post number");
        String comment = mapper.getString("comment");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(commented_user))
            {
                for (Post post : user.getPosts())
                {
                    if(post.getPostNumber() == postNumber)
                    {
                        post.comment(commenting_user, comment);
                        DataBase.writeData();
                    }
                }
            }
        }

        try
        {
            Main.onlineUsers_data.get(commented_user).getWriter().writeObject(Tasks.getCommentTask(commenting_user,
                    String.valueOf(postNumber), comment));
        }
        catch (NullPointerException e) {}
    }

    private void like() throws IOException, JSONException
    {
        String liker = mapper.getString("liker"), liked = mapper.getString("liked");
        int postNumber = mapper.getInt("post number");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(liked))
            {
                for (Post post : user.getPosts())
                {
                    if(post.getPostNumber() == postNumber)
                    {
                        post.like(liker);
                        DataBase.writeData();
                    }
                }
            }
        }

        try
        {
            Main.onlineUsers_data.get(liked).getWriter().writeObject(Tasks.getLikeTask(liker,
                    String.valueOf(postNumber)));
        }
        catch (NullPointerException e) {}
    }

    private void getPosts() throws IOException, JSONException
    {
        String username = mapper.getString("username");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(username))
            {
                ArrayList<Post> posts = new ArrayList<>();
                for (Post post : user.getPosts())
                {
                    posts.add(post);
                }

                writer.writeObject(Tasks.getReadTask());
                writer.writeObject(posts);
            }
        }
    }

    private void profile() throws IOException, JSONException
    {
        String name = mapper.getString("username");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(name))
            {
                writer.writeObject(Tasks.getReadTask());
                writer.writeObject(user.getProfile_photo());
            }
        }
    }

    private void unfollow() throws IOException, JSONException
    {
        String unfollowed = mapper.getString("username");
        String unfollower = mapper.getString("unfollower");

        for (User user : Main.users)
        {
            if(user.getUsername().equals(unfollowed))
            {
                user.getFollowers().remove(unfollower);
            }

            if(user.getUsername().equals(unfollower))
            {
                user.getFollowings().remove(unfollowed);
            }
        }

        // send to online user
        try
        {
            Main.onlineUsers_data.get(unfollowed).getWriter().writeObject(Tasks.getUnfollowTask(unfollower));
        }
        catch (NullPointerException e) {}

        DataBase.writeData();
    }

    private void follow() throws IOException, JSONException
    {
        String followed = mapper.getString("username");
        String follower = mapper.getString("follower");

        if(Main.users.stream().filter(user -> user.getUsername().equals(followed)).count() == 0)
        {
            writer.writeObject(Tasks.getReadTask());
            writer.writeObject(false);
        }

        else
        {
            writer.writeObject(Tasks.getReadTask());
            writer.writeObject(true);

            for (User user : Main.users)
            {
                if(user.getUsername().equals(followed))
                {
                    user.getFollowers().add(follower);
                }

                if(user.getUsername().equals(follower))
                {
                    user.getFollowings().add(followed);
                }
            }

            DataBase.writeData();

            // send notification for user
            try
            {
                Main.onlineUsers_data.get(followed).getWriter().writeObject(Tasks.getFollowTask(follower));
            }
            catch (NullPointerException e) {}
        }
    }

    private void newProfile() throws IOException, JSONException
    {
        String name = mapper.getString("username");
        int len = reader.readInt();
        byte[] data = new byte[len];
        reader.readFully(data);

        for (User user : Main.users)
        {
            if(user.getUsername().equals(name))
            {
                user.setProfile(data);
                DataBase.writeData();

                break;
            }
        }
    }

    private void newPost() throws IOException, JSONException
    {
        int len = reader.readInt();
        byte[] data = new byte[len];
        reader.readFully(data);
        String description = reader.readUTF();
        String username = mapper.getString("username");


        for (User user : Main.users)
        {
            if(user.getUsername().equals(username))
            {
                Post post = new Post(data, description, user.getPosts().size() + 1);
                post.comment(username, description);
                user.getPosts().add(post);
                DataBase.writeData();
            }
        }

    }

    private void exit() throws JSONException, IOException
    {
        String username = mapper.getString("username");

        try
        {
            Main.onlineUsers.keySet().removeIf(user -> user.getUsername().equals(username));
            Main.onlineUsers_data.keySet().removeIf(s -> s.equals(username));
        }
        catch (ConcurrentModificationException e) {}

        Main.server.kill(mapper.getInt("number"));

        reader.close();
        writer.close();
    }

    private void checkUsername() throws JSONException, IOException
    {
        boolean exists = true;

        for (User user : Main.users)
        {
            if(user.getUsername().equals(mapper.getString("username")))
            {
                writer.writeObject(true);
                exists = false;
            }
        }

        if(exists)
        {
            writer.writeObject(false);
        }
    }

    private void signUp() throws JSONException, IOException
    {
        User user = new User(mapper.getString("username"), mapper.getString("password"));
        Main.users.add(user);
        DataBase.writeData();
    }

    private void login() throws JSONException, IOException
    {
        String username = mapper.getString("username"), password = mapper.getString("password");
        boolean flag = true;
        boolean isOnline = false;

        for (User user : Main.onlineUsers.keySet())
        {
            if(user.getUsername().equals(username))
            {
                isOnline = true;
                writer.writeObject(false);
            }
        }

        if(!isOnline)
        {
            for (User user : Main.users)
            {
                if(user.getUsername().equals(username) && user.getPassword().equals(password))
                {
                    writer.writeObject(true);
                    int clientNumber = mapper.getInt("number");

                    TaskListener clientListener = Server.getKiller().get(clientNumber);
                    Main.onlineUsers_data.put(username, clientListener);

                    Socket userSocket = (Socket) Main.server.getConnections().get(clientNumber);
                    Main.onlineUsers.put(user, userSocket);

                    flag = false;

                    writer.writeObject(user);
                }
            }

            if(flag)
            {
                writer.writeObject(false);
            }
        }
    }
}
