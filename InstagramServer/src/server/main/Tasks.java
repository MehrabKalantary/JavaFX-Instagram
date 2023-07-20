package server.main;

public interface Tasks
{
    String read = "{\"task\":\"read\"}";
    String follow = "{\"task\":\"follow\",\"follower\":\"%s\"}";
    String unfollow = "{\"task\":\"unfollow\",\"unfollower\":\"%s\"}";
    String like = "{\"task\":\"like\",\"liker\":\"%s\",\"post number\":\"%s\"}";
    String commentTask = "{\"task\":\"comment\",\"username\":\"%s\",\"post number\":\"%s\",\"comment\":\"%s\"}";
    String newDirect = "{\"task\":\"new direct\",\"from\":\"%s\"}";
    String newMessage = "{\"task\":\"new message\",\"from\":\"%s\",\"message\":\"%s\"}";

    static String getNewMessageTask(String from, String message)
    {
        return String.format(newMessage, from, message);
    }

    static String getNewDirectTask(String from)
    {
        return String.format(newDirect, from);
    }

    static String getCommentTask(String username, String postNumber, String comment)
    {
        return String.format(commentTask, username, postNumber, comment);
    }

    static String getLikeTask (String liker, String number)
    {
        return String.format(like, liker, number);
    }

    static String getReadTask()
    {
        return read;
    }

    static String getFollowTask(String username)
    {
        return String.format(follow, username);
    }

    static String getUnfollowTask(String username)
    {
        return String.format(unfollow, username);
    }
}
