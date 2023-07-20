package data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Direct implements Serializable
{
    private Direct.Username from_user, to_user;

    // common object between users in a direct
    private ArrayList<String> messages;

    public Direct(String to_user, String from_user)
    {
        this.from_user = new Username(from_user);
        this.to_user = new Username(to_user);
        messages = new ArrayList<>();
    }

    public String getFrom_user()
    {
        return from_user.getName();
    }

    public String getTo_user()
    {
        return to_user.getName();
    }

    public ArrayList<String> getMessages()
    {
        return messages;
    }

    public void newMessage(String username, String message)
    {
        messages.add(username + " : " + message);
    }


    public static class Username implements Serializable
    {
        private String name;

        public Username(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
}
