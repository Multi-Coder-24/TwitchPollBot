package org.multicoder.pollbot;

import org.multicoder.pollbot.gui.Screen;
import org.multicoder.pollbot.twitch.Config;
import org.multicoder.pollbot.twitch.Connection;
import org.multicoder.pollbot.util.UpdateChecker;

import javax.swing.*;
import java.net.URL;

public class Main
{
    //  Helper Classes
    public static Config config;
    public static Connection connection;
    public static Screen screen;
    //  Prefetched Icon from resources directory
    public static URL ICON;

    //  Main Entry Point for the application
    public static void main(String[] args)
    {
        try{
            //  Version check against github releases
            UpdateChecker.CheckForUpdate(System.getProperty("user.home"));
            //  Prefetches the Icon Image before GUI uses it.
            ClassLoader classLoader = Main.class.getClassLoader();
            ICON = classLoader.getResource("icon.png");
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }
}
