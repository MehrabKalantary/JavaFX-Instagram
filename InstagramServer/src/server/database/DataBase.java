package server.database;

import data.User;
import server.main.Main;

import java.io.*;
import java.util.List;

public class DataBase
{
    public static void readData() throws IOException
    {
        FileInputStream file = null;
        ObjectInputStream reader = null;

        try
        {
            file = new FileInputStream("database.txt");
            reader = new ObjectInputStream(file);
            Main.users = (List<User>) reader.readObject();
        }

        catch (Exception e) {}

        finally
        {
            try
            {
                file.close();
                reader.close();
            }
            catch (NullPointerException e) {}
        }

    }

    public static void writeData() throws IOException
    {
        FileOutputStream file = new FileOutputStream("database.txt");
        ObjectOutputStream writer = new ObjectOutputStream(file);
        writer.writeObject(Main.users);

        file.close();
        writer.close();
    }
}
