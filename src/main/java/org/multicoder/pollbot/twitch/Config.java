package org.multicoder.pollbot.twitch;

import com.electronwill.nightconfig.core.file.FileConfig;

import javax.swing.*;
import java.io.File;


public class Config
{
    //  Config Properties that get used elsewhere
    public String ChannelName;
    public String ClientID;
    public String ClientSecret;
    public String Preset1;
    public String Preset2;
    public String Preset3;
    public String votePrefix;

    //  Used for simple version checking
    public final String Version = "1.1.0";

    //  Main Constructor
    public Config()
    {
        try{
            String Location = System.getProperty("user.home") + "\\pollbot.toml";
            File TOML_FILE = new File(Location);
            //  First Run
            if(!TOML_FILE.exists())
            {
                FileConfig cfg = FileConfig.of(TOML_FILE);
                cfg.add("ClientID","changeme");
                cfg.add("ClientSecret","changeme");
                cfg.add("ChannelName","changeme");
                cfg.add("preset_1","One,Two,Three");
                cfg.add("preset_2","One,Two,Three");
                cfg.add("preset_3","One,Two,Three");
                cfg.add("votePrefix","!vote");
                cfg.add("Version",Version);
                cfg.save();
                ChannelName = "changeme";
                ClientID = "changeme";
                ClientSecret = "changeme";
                Preset1 = "One,Two,Three";
                Preset2 = "One,Two,Three";
                Preset3 = "One,Two,Three";
                votePrefix = "!vote";
            }
            else
            {
                FileConfig cfg = FileConfig.of(TOML_FILE);
                cfg.load();
                //  Version is up to date
                if(cfg.get("Version").equals(Version))
                {
                    ChannelName = cfg.get("ChannelName");
                    ClientID = cfg.get("ClientID");
                    ClientSecret = cfg.get("ClientSecret");
                    Preset1 = cfg.get("preset_1");
                    Preset2 = cfg.get("preset_2");
                    Preset3 = cfg.get("preset_3");
                    votePrefix = cfg.get("votePrefix");
                }
                //  Version is out of date, recreate file
                else
                {
                    cfg = FileConfig.of(TOML_FILE);
                    cfg.add("ClientID","changeme");
                    cfg.add("ClientSecret","changeme");
                    cfg.add("ChannelName","changeme");
                    cfg.add("preset_1","One,Two,Three");
                    cfg.add("preset_2","One,Two,Three");
                    cfg.add("preset_3","One,Two,Three");
                    cfg.add("votePrefix","!vote");
                    cfg.add("Version",Version);
                    cfg.save();
                    ChannelName = "changeme";
                    ClientID = "changeme";
                    ClientSecret = "changeme";
                    Preset1 = "One,Two,Three";
                    Preset2 = "One,Two,Three";
                    Preset3 = "One,Two,Three";
                    votePrefix = "!vote";
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Critical Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
