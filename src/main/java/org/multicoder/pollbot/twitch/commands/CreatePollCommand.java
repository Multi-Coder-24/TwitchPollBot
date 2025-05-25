package org.multicoder.pollbot.twitch.commands;

import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.util.VotesManager;

public class CreatePollCommand
{
    public static void Trigger(String Args)
    {
        String[] Options = Args.split(",");
        Main.screen.MainApp.ResetButton.doClick();
        for(String Option : Options){
            VotesManager.AddOption(Option);
            VotesManager.Votes.add(0);
            Option += ", 0";
            Main.screen.MainApp.Votes.addElement(Option);
        }
        VotesManager.Voted_Users.clear();
    }
}
