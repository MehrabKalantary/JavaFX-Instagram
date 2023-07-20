package server.main;

import data.User;
import server.database.DataBase;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    public static Server server;
    public static List<User> users = new ArrayList<>();

    // if a user logins, it will be online
    public static Map<User, Socket> onlineUsers = new HashMap<>();
    public static Map<String, TaskListener> onlineUsers_data = new HashMap<>();

    public static void main(String[] args) throws IOException
    {
        DataBase.readData();
        server = new Server();

        while (true)
        {
            server.waitForConnection();

            TaskListener listener = new TaskListener(server.getWriter(), server.getReader());

            server.setKiller(listener);

            Thread thread = new Thread(listener);

            thread.start();
        }
    }
}
