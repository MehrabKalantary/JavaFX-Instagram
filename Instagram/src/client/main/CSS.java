package client.main;

public class CSS
{
    private static final String colorStyle = "-fx-text-inner-color: %s;";

    public static String getTextColor(String color)
    {
        return String.format(colorStyle, color);
    }
}
