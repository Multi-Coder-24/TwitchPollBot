package org.multicoder.pollbot.twitch;

import org.multicoder.pollbot.Main;

import javax.swing.*;
import java.util.*;

public class VotesManager
{
    public static List<String> Voted_Users = new ArrayList<>();
    public static List<String> Options = new ArrayList<>();
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
        catch(NumberFormatException ignored)
        {
            try{
                String Option = message.split(" ",2)[1];
                int Index = Options.indexOf(Option.toLowerCase(Locale.ROOT));
                if(Index != -1)
                {
                    System.out.println(Option);
                    count_vote_indexed(Index,Username);
                }
            } catch (Exception ignored2) {}
        }

    }
    private static void count_vote(int Selection, String Username)
    {
        int currentValue = Votes.get(Selection - 1);
        currentValue++;
        Votes.set(Selection - 1, currentValue);
        Voted_Users.add(Username);
        post_vote_update(Selection - 1,currentValue);
    }
    private static void count_vote_indexed(int Selection, String Username)
    {
        int currentValue = Votes.get(Selection);
        currentValue++;
        Votes.set(Selection, currentValue);
        Voted_Users.add(Username);
        String Option = Main.screen.Votes.get(Selection).split(",")[0];
        post_vote_update_indexed(Selection,currentValue,Option);
    }
    private static void post_vote_update(int Selection, int Value)
    {

        DefaultListModel<String> model = Main.screen.Votes;
        String Message = model.get(Selection - 1).split(",")[0];
        Message += ", " + Value;
        model.setElementAt(Message,Selection);
    }
    private static void post_vote_update_indexed(int Selection, int Value,String Option)
    {
        DefaultListModel<String> model = Main.screen.Votes;
        String NewValue = Option + ", " + Value;
        model.set(Selection,NewValue);
    }
    public static void FetchWinner()
    {
        DefaultListModel<String> model = Main.screen.Votes;
        int WinningValue = Votes.stream().max(Comparator.naturalOrder()).orElse(-1);
        if(WinningValue == -1){return;}
        List<Integer> WinningVotes = getAllIndexes(Votes,WinningValue);
        if(WinningVotes.size() == 1)
        {
            String Winner = model.get(WinningVotes.getFirst());
            Winner = Winner.split(",")[0];
            JOptionPane.showMessageDialog(null, Winner,"Results",JOptionPane.INFORMATION_MESSAGE);
            Main.connection.chat.sendMessage(Main.config.Username,"The Winning Option is " + Winner);
        }
        else
        {
            StringBuilder Winners = new StringBuilder();
            Winners.append("Winning Options Are: ");
            for(int winningIndex : WinningVotes)
            {
                Winners.append(model.get(winningIndex).split(",")[0]);
                Winners.append(", ");
            }
            JOptionPane.showMessageDialog(Main.screen, Winners.toString(),"Results",JOptionPane.INFORMATION_MESSAGE);
            Main.connection.chat.sendMessage(Main.config.Username,Winners.toString());
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
