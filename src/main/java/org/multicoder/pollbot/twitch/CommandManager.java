package org.multicoder.pollbot.twitch;

import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.twitch.commands.CreatePollCommand;
import org.multicoder.pollbot.twitch.commands.PresetPollCommand;
import org.multicoder.pollbot.twitch.commands.StartPollCommand;
import org.multicoder.pollbot.twitch.commands.UpdatePresetPollCommand;

public class CommandManager
{
    public static void HandleCommand(String Username, String Message)
    {
        if(Main.screen.connection.moderators.contains(Username) || Main.screen.config.Username.equals(Username))
        {
            String Command = Message.split(" ")[1].toLowerCase();
            String Args = Message.split(" ")[2];
            switch (Command)
            {
                case "create":
                    CreatePollCommand.Trigger(Args);
                    break;
                case "start":
                    StartPollCommand.Trigger(Args);
                    break;
                case "preset":
                    PresetPollCommand.Trigger(Args);
                    break;
                case "udpreset":
                    UpdatePresetPollCommand.Trigger(Args);
                    break;
                default:
                    break;
            }
        }
        else {
            Main.screen.connection.chat.sendMessage(Main.screen.config.Username,"@" + Username + " You do not have permission to use poll commands\nRepeat offences may result in action from moderators");
        }
    }
}
