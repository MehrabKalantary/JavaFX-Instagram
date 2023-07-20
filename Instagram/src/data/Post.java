package data;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post implements Serializable
{
    private String date, time, description;
    private byte[] photo;
    private Map<String, String> comments;

    public List<String> getLiking_users()
    {
        return liking_users;
    }

    private List<String> liking_users;
    private int likes, postNumber;

    public Post(byte[] photo, String description, int number)
    {
        this.photo = photo;
        this.description = description;
        this.postNumber = number;

        date = LocalDate.now().toString();
        String timeFormat = LocalTime.now().toString();
        time = timeFormat.substring(0, timeFormat.lastIndexOf(":"));

        comments = new HashMap<>();
        liking_users = new ArrayList<>();
        likes = 0;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void comment(String username, String comment)
    {
        if(comment.trim().length() != 0)
        {
            comments.put(username, comment);
        }
    }

    public void like(String username)
    {
        if(!liking_users.contains(username))
        {
            likes++;
            liking_users.add(username);
        }
    }
}
