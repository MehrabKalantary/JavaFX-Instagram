package client.main;

public interface Tasks
{
    String signUp = "{\"task\":\"signup\",\"username\":\"%s\",\"password\":\"%s\"}";
    String checkUsername = "{\"task\":\"check username\",\"username\":\"%s\"}";
    String login = "{\"task\":\"login\",\"username\":\"%s\",\"password\":\"%s\"," +
            "\"number\":\"" + Main.client.getNumber() + "\"}";
    String exit = "{\"task\":\"exit\",\"username\":\"%s\",\"number\":\"" + Main.client.getNumber() + "\"}";
    String newPost = "{\"task\":\"new post\",\"username\":\"%s\"}";
    String newProfile = "{\"task\":\"new profile\",\"username\":\"%s\"}";
    String follow = "{\"task\":\"follow\",\"username\":\"%s\",\"follower\":\"%s\"}";
    String unfollow = "{\"task\":\"unfollow\",\"username\":\"%s\",\"unfollower\":\"%s\"}";
    String profile = "{\"task\":\"profile\",\"username\":\"%s\"}";
    String getPosts = "{\"task\":\"get posts\",\"username\":\"%s\"}";
    String like = "{\"task\":\"like\",\"liker\":\"%s\",\"liked\":\"%s\",\"post number\":\"%s\"}";
    String comment = "{\"task\":\"comment\",\"username\":\"%s\",\"commented\":\"%s\",\"post number\":\"%s\"," +
            "\"comment\":\"%s\"}";
    String newDirect = "{\"task\":\"new direct\",\"from\":\"%s\",\"to\":\"%s\"}";
    String newMessage = "{\"task\":\"new message\",\"from\":\"%s\",\"to\":\"%s\",\"message\":\"%s\"}";

    static String getNewMessageTask(String from, String to, String message)
    {
        return String.format(newMessage, from, to, message);
    }

    static String getNewDirectTask(String from, String to)
    {
        return String.format(newDirect, from, to);
    }

    static String getCommentTask(String username, String commented, String number, String commentText)
    {
        return String.format(comment, username, commented, number, commentText);
    }


    static String getLikeTask(String liker, String liked, String number)
    {
        return String.format(like, liker, liked, number);
    }

    static String getPostsTask(String username)
    {
        return String.format(getPosts, username);
    }

    static String getSignUpTask(String username, String password)
    {
        return String.format(signUp, username, password);
    }

    static String getCheckUsernameTask(String username)
    {
        return String.format(checkUsername, username);
    }

    static String getLoginTask(String username, String password)
    {
        return String.format(login, username, password);
    }

    static String getExitTask(String username)
    {
        return String.format(exit, username);
    }

    static String getNewPostTask(String name)
    {
        return String.format(newPost, name);
    }

    static String getNewProfileTask(String name)
    {
        return String.format(newProfile, name);
    }

    static String getFollowTask(String username)
    {
        return String.format(follow, username, Main.user.getUsername());
    }

    static String getUnfollowTask(String username)
    {
        return String.format(unfollow, username, Main.user.getUsername());
    }

    static String getProfileTask(String name)
    {
        return String.format(profile, name);
    }
}
