package org.multicoder.pollbot.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;

public class UpdateChecker
{
    private static final int Version = 30100;
    private static final String GITHUB_RELEASES = "https://github.com/Multi-Coder-24/TwitchPollBot/releases/latest";
    private static final String Splitter = "·";

    public static void CheckForUpdate(String UserFolder) throws Exception
    {
        Document doc = Jsoup.parse(new URI(GITHUB_RELEASES).toURL(),5000);
        String LatestVersion = doc.title().split(Splitter)[0].split("v")[1].trim();
        int LatestVersionInt = Integer.parseInt(LatestVersion.replace('.', '0'));
        if(LatestVersionInt > Version)
        {
            //  Out of date, Prefetch updated zip
            String DownloadPath = doc.location().split("tag/")[0] +
                    "download/" + doc.location().split("tag/")[1] +
                    "/TwitchPollBot-" + LatestVersion + ".zip";
            Files.copy(new URI(DownloadPath).toURL().openStream(),new File(UserFolder + "/TwitchPollBot-"+LatestVersion + ".zip").toPath());
            JOptionPane.showMessageDialog(null,"This app has an update\nThe new version can be found at: " + new File(UserFolder + "/TwitchPollBot-"+LatestVersion + ".zip").getAbsolutePath() ,"TwitchPollBot Update Checker",JOptionPane.INFORMATION_MESSAGE);
            throw new Exception("Outdated application");
        }
        else{
            JOptionPane.showMessageDialog(null,"The Application is up to date","TwitchPollBot Update Checker",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
