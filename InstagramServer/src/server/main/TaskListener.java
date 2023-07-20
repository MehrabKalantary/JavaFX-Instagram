package server.main;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * this runnable waits until a message come from client
 */

public class TaskListener implements Runnable
{
    private ObjectOutputStream writer;

    public ObjectOutputStream getWriter()
    {
        return writer;
    }

    public DataInputStream getReader()
    {
        return reader;
    }

    private DataInputStream reader;
    private String message;
    private JSONObject mapper;
    private boolean continues = true;

    public TaskListener(ObjectOutputStream writer, DataInputStream reader)
    {
        this.writer = writer;
        this.reader = reader;
    }

    public void kill()
    {
        continues = false;
    }

    @Override
    public void run()
    {
        while (continues)
        {
            try
            {
                message = reader.readUTF();
                mapper = new JSONObject(message);

                TaskHandler handler = new TaskHandler(mapper, writer, reader);
                handler.handle();

            }
            catch (IOException | JSONException e) {}
        }
    }
}
