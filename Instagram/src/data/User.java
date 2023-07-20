package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable
{
    private String username, password;
    private byte[] profile_photo;
    private List<String> followers, followings;

    public List<Direct> getDirects()
    {
        return directs;
    }

    private List<Direct> directs;


    public void newDirect(String username)
    {
        Direct direct = new Direct(username, this.username);
        directs.add(direct);
    }

    public byte[] getProfile_photo()
    {
        return profile_photo;
    }

    public List<String> getFollowers()
    {
        return followers;
    }

    public List<String> getFollowings()
    {
        return followings;
    }

    public void setProfile(byte[] data)
    {
        profile_photo = data;
    }

    private ArrayList<Post> posts;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;

        followers = new ArrayList<>();
        followings = new ArrayList<>();
        posts = new ArrayList<>();
        directs = new ArrayList<>();
    }

    public ArrayList<Post> getPosts()
    {
        return posts;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean equals(Object obj)
    {
        User user = (User) obj;
        return user.equals(this.username);
    }

    @Override
    public int hashCode()
    {
        return this.username.hashCode();
    }
}
