package org.multicoder.pollbot.util;

import com.google.gson.*;
import org.multicoder.pollbot.Main;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class JsonConfig
{
    public String Username;
    public String ClientID;
    public String AccessToken;

    public String VotePrefix;
    public String PollCommandPrefix;
    public String[] Preset_1;
    public String[] Preset_2;
    public String[] Preset_3;
    private static final String[] PresetConst = new String[] {"One","Two","Three"};

    public JsonConfig()
    {
        try
        {
            File ConfigFile = new File(System.getProperty("user.home") + "\\pollbot.json");
            if(!ConfigFile.exists())
            {
                JsonObject Root = new JsonObject();
                JsonObject TwitchConfig = new JsonObject();
                JsonObject PollBotConfig = new JsonObject();
                JsonArray DefaultPreset = new JsonArray();
                DefaultPreset.add(PresetConst[0]);
                DefaultPreset.add(PresetConst[1]);
                DefaultPreset.add(PresetConst[2]);
                TwitchConfig.addProperty("Username", "changeme");
                TwitchConfig.addProperty("ClientID", "changeme");
                TwitchConfig.addProperty("AccessToken", "changeme");
                PollBotConfig.addProperty("VotePrefix", "!vote");
                PollBotConfig.addProperty("PollCommandPrefix", "!poll");
                PollBotConfig.add("Preset_1",DefaultPreset);
                PollBotConfig.add("Preset_2",DefaultPreset);
                PollBotConfig.add("Preset_3",DefaultPreset);
                Root.add("TwitchConfig", TwitchConfig);
                Root.add("PollBotConfig", PollBotConfig);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                try (FileWriter fw = new FileWriter(ConfigFile)) {
                    gson.toJson(Root,fw);
                }
                JOptionPane.showMessageDialog(null,"New Blank Configuration Created\nPlease edit the file located at: " + ConfigFile.getAbsolutePath(),"New Config",JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException("New Config");
            }
            else
            {
                JsonElement preRoot = JsonParser.parseReader(new FileReader(System.getProperty("user.home") + "\\pollbot.json"));
                JsonObject Root = preRoot.getAsJsonObject();
                JsonObject TwitchConfig = Root.getAsJsonObject("TwitchConfig");
                JsonObject PollBotConfig = Root.getAsJsonObject("PollBotConfig");
                Username = TwitchConfig.get("Username").getAsString();
                ClientID = TwitchConfig.get("ClientID").getAsString();
                AccessToken = TwitchConfig.get("AccessToken").getAsString();
                VotePrefix = PollBotConfig.get("VotePrefix").getAsString();
                PollCommandPrefix = PollBotConfig.get("PollCommandPrefix").getAsString();
                List<JsonElement> Preset1Json = PollBotConfig.getAsJsonArray("Preset_1").asList();
                List<JsonElement> Preset2Json = PollBotConfig.getAsJsonArray("Preset_2").asList();
                List<JsonElement> Preset3Json = PollBotConfig.getAsJsonArray("Preset_3").asList();
                List<String> CacheList = new ArrayList<>();
                Preset1Json.forEach(x ->
                        CacheList.add(x.getAsString()));
                Preset_1 = CacheList.toArray(new String[0]);
                CacheList.clear();
                Preset2Json.forEach(x -> CacheList.add(x.getAsString()));
                Preset_2 = CacheList.toArray(new String[0]);
                CacheList.clear();
                Preset3Json.forEach(x -> CacheList.add(x.getAsString()));
                Preset_3 = CacheList.toArray(new String[0]);
            }
        } catch (Exception e)
        {
            Main.ERRLOG.error("Unknown Config Error",e);
        }
    }
}
