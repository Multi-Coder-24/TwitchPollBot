package org.multicoder.pollbot;

import org.multicoder.pollbot.gui.Screen;
import org.multicoder.pollbot.util.*;
import org.slf4j.*;
import javax.swing.*;
import java.io.File;
import java.net.URL;

public class Main
{
    //  Helper Classes
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
            Main.LOG.info("Starting TwitchPollBot...");
            Main.LOG.info("Adding Shutdown Hook...");
            Runtime rt = Runtime.getRuntime();
            rt.addShutdownHook(new Thread(() -> {File CD = new File("");File[] Files = CD.getAbsoluteFile().listFiles();if(Files != null){for(File file : Files){if(file.isFile() && file.length() == 0) file.getAbsoluteFile().deleteOnExit();}}}));
            LOG.info("Running Version Check...");
            //  Version check against github releases
            UpdateChecker.CheckForUpdate(System.getProperty("user.home"));
            //  Prefetches the Icon Image before GUI uses it.
            LOG.info("Prefetching Icon...");
            ClassLoader classLoader = Main.class.getClassLoader();
            ICON = classLoader.getResource("icon.png");
            //  Launches the main app gui
            LOG.info("Launching The UI...");
            screen = new Screen();
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"An Unknown Error Has Occurred\nPlease Submit a bug report and attach the TPBError log","Error",JOptionPane.ERROR_MESSAGE);
            ERRLOG.error("Unknown Error Has Occurred",e);
        }
    }
}
