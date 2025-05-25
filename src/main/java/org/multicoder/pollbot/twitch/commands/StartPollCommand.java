package org.multicoder.pollbot.twitch.commands;

import org.multicoder.pollbot.Main;

public class StartPollCommand
{
    public static void Trigger(String Args)
    {
        Main.screen.MainApp.DurationField.setText(Args);
        Main.screen.MainApp.StartButton.doClick();
    }
}
