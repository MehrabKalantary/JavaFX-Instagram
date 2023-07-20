package client.main;

import java.io.*;
import java.net.Socket;

public class Client
{
    private final int port = 8080;
    private final String ip = "localhost";
    private Socket socket;
    private DataOutputStream writer;
    private ObjectInputStream reader;

    // a primary key for each client to connect it to its socket
    private int connectionNumber;

    public DataOutputStream getWriter()
    {
        return writer;
    }

    public ObjectInputStream getReader()
    {
        return reader;
    }

    public void connect() throws IOException, ClassNotFoundException
    {
        socket = new Socket(ip, port);
        writer = new DataOutputStream(socket.getOutputStream());
        reader = new ObjectInputStream(socket.getInputStream());

        connectionNumber = (int) reader.readObject();
    }

    public int getNumber()
    {
        return connectionNumber;
    }

    public Socket getSocket() {
        return socket;
    }
}
