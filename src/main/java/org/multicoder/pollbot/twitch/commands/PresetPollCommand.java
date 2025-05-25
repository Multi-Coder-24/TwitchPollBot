package org.multicoder.pollbot.twitch.commands;

import org.multicoder.pollbot.Main;

public class PresetPollCommand
{
    public static void Trigger(String Args)
    {
        try{
            int Preset = Integer.parseInt(Args);
            Main.screen.MainApp.ResetButton.doClick(100);
            switch (Preset){
                case 1:
                    Main.screen.MainApp.Preset1.doClick(100);
                    break;
                case 2:
                    Main.screen.MainApp.Preset2.doClick(100);
                    break;
                case 3:
                    Main.screen.MainApp.Preset3.doClick(100);
                    break;
            }
        }
        catch(Exception ignored){}
    }
}
