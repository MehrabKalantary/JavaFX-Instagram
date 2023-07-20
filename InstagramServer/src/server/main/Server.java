package server.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Server
{
    private final int port = 8080;
    private ServerSocket server;
    private Socket socket;
    private ObjectOutputStream writer;
    private DataInputStream reader;

    // number of connections -> primary key for clients
    private static int connection = 0;

    // a temp map to help connecting USER to SOCKET
    private static Map<Integer, Socket> connections = new HashMap<>();

    // to kill client thread when app gets closed
    private static Map<Integer, TaskListener> killer = new HashMap<>();

    public void setKiller(TaskListener listener)
    {
        killer.put(connection, listener);
    }

    public static Map<Integer, TaskListener> getKiller()
    {
        return killer;
    }

    // kill a thread
    public void kill(int number) throws IOException
    {
        killer.get(number).kill();
        killer.remove(number);

        connections.get(number).close();
        connections.remove(number);
    }

    public Server() throws IOException
    {
        server = new ServerSocket(port);
    }

    public void waitForConnection() throws IOException
    {
        socket = server.accept();
        writer = new ObjectOutputStream(socket.getOutputStream());
        reader = new DataInputStream(socket.getInputStream());

        connection ++;
        connections.put(connection, socket);

        // send connection number for client
        writer.writeObject(connection);
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public DataInputStream getReader() {
        return reader;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public Map getConnections()
    {
        return connections;
    }
}
