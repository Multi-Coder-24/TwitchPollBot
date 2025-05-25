package org.multicoder.pollbot.twitch.commands;

import org.multicoder.pollbot.Main;

public class UpdatePresetPollCommand
{
    public static void Trigger(String Args)
    {
        String[] ArgsArr = Args.split(" ",1);
        try{
            int Preset = Integer.parseInt(ArgsArr[0]);
            String[] Options = ArgsArr[1].split(",");
            switch (Preset){
                case 1:
                    Main.screen.config.Preset_1 = Options;
                    break;
                case 2:
                    Main.screen.config.Preset_2 = Options;
                    break;
                case 3:
                    Main.screen.config.Preset_3 = Options;
                    break;
            }
            Main.screen.config.UpdateConfig();
        }
        catch(Exception ignored){}
    }
}
