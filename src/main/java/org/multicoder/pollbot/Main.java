package org.multicoder.pollbot;

import org.multicoder.pollbot.config.JsonConfig;
import org.multicoder.pollbot.gui.ConfigScreen;
import org.multicoder.pollbot.gui.Screen;
import org.multicoder.pollbot.twitch.Connection;
import org.multicoder.pollbot.util.*;
import org.slf4j.*;
import javax.swing.*;
import java.io.File;
import java.net.URL;

public class Main
{
    //  Helper Classes
    public static JsonConfig config;
    public static Connection connection;
    public static Screen screen;
    //  Prefetched Icon from resources directory
    public static URL ICON;
    public static Logger LOG = LoggerFactory.getLogger("TwitchPollBot");
    public static Logger ERRLOG = LoggerFactory.getLogger("TwitchPollBotError");
    //  Main Entry Point for the application
    public static void main(String[] args)
    {
        try
        {
            Runtime rt = Runtime.getRuntime();
            rt.addShutdownHook(new Thread(() -> {File CD = new File("");File[] Files = CD.getAbsoluteFile().listFiles();if(Files != null){for(File file : Files){if(file.isFile() && file.length() == 0) file.getAbsoluteFile().deleteOnExit();}}}));
            LOG.info("Twitch Poll Bot Started");
            LOG.info("Running Version Check");
            //  Version check against github releases
            UpdateChecker.CheckForUpdate(System.getProperty("user.home"));
            //  Prefetches the Icon Image before GUI uses it.
            LOG.info("Prefetching Icon");
            ClassLoader classLoader = Main.class.getClassLoader();
            ICON = classLoader.getResource("icon.png");
            //  Fetches config and saves to the config property
            LOG.info("Loading Configuration");
            config = new JsonConfig();
            //  Basic version or first run check
            LOG.info("Checking For Un-configured File");
            if(config.Username.equals("changeme"))
            {
                LOG.info("Un-configured File, Loading Config Edit Screen");
                System.out.println("First Run or Old Version Detected. Please Edit Config");
                new ConfigScreen();
            }
            else
            {
                //  Starts the twitch bot using the values in the config
                LOG.info("Connecting To Twitch API");
                connection = new Connection();
                //  Launches the main app gui
                LOG.info("Launching The UI");

                screen = new Screen();
            }
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            ERRLOG.error("Unknown Error Has Occured",e);
        }
    }
}
