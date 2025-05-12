package org.multicoder.pollbot;

import org.multicoder.pollbot.gui.Screen;
import org.multicoder.pollbot.twitch.Config;
import org.multicoder.pollbot.twitch.Connection;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main
{
    //  Helper Classes
    public static Config config;
    public static Connection connection;
    public static Screen screen;

    //  Main Entry Point for the application
    public static void main(String[] args)
    {
        //  Fetches config and saves to the config property
        config = new Config();
        //  Basic version or first run check
        if(config.ChannelName.equals("changeme"))
        {
            System.out.print("First Run or Old Version Detected. Please Edit Config");
            System.exit(0);
        }
        else
        {
            //  Starts the twitch bot using the values in the config
            connection = new Connection();
            //  Launches the main app gui
            screen = new Screen();
        }
    }
}
