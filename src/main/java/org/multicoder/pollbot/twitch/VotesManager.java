package org.multicoder.pollbot.twitch;

import org.multicoder.pollbot.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VotesManager
{
    public static List<String> Voted_Users = new ArrayList<>();
    public static List<Integer> Votes = new ArrayList<>();

    public static void vote_trigger(String message, String Username)
    {
        try
        {
            int Option = Integer.parseInt(message.split(" ")[1]);
            if(!Voted_Users.contains(Username))
            {
                count_vote(Option,Username);
            }
        }
        catch(Exception ignored) {}

    }
    private static void count_vote(int Selection, String Username)
    {
        int currentValue = Votes.get(Selection - 1);
        currentValue++;
        Votes.set(Selection - 1, currentValue);
        Voted_Users.add(Username);
        post_vote_update(Selection - 1,currentValue);
    }
    private static void post_vote_update(int Selection, int Value)
    {
        String Message = Main.screen.Votes.getItem(Selection);
        Message = Message.split(",")[0];
        Message += ", " + Value;
        Main.screen.Votes.replaceItem(Message,Selection);
    }
    public static void FetchWinner()
    {
        int WinningValue = Votes.stream().max(Comparator.naturalOrder()).orElse(-1);
        if(WinningValue == -1){return;}
        List<Integer> WinningVotes = getAllIndexes(Votes,WinningValue);
        if(WinningVotes.size() == 1)
        {
            String Winner = Main.screen.Votes.getItem(WinningVotes.getFirst());
            Winner = Winner.split(",")[0];
            JOptionPane.showMessageDialog(Main.screen, Winner,"Results",JOptionPane.INFORMATION_MESSAGE);
            Main.connection.chat.sendMessage(Main.config.ChannelName,"The Winning Option is " + Winner);
        }
        else
        {
            StringBuilder Winners = new StringBuilder();
            Winners.append("Winning Options Are: ");
            for(int winningIndex : WinningVotes)
            {
                Winners.append(Main.screen.Votes.getItem(winningIndex).split(",")[0]);
                Winners.append(", ");
            }
            JOptionPane.showMessageDialog(Main.screen, Winners.toString(),"Results",JOptionPane.INFORMATION_MESSAGE);
            Main.connection.chat.sendMessage(Main.config.ChannelName,Winners.toString());
        }
    }

    private static List<Integer> getAllIndexes(List<Integer> list, int target) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == target) {
                indexes.add(i);
            }
        }
        return indexes;
    }
}
