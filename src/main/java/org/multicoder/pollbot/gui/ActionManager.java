package org.multicoder.pollbot.gui;

import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.twitch.VotesManager;

import java.awt.event.ActionEvent;

public class ActionManager
{
    public static void HandleAction(ActionEvent event)
    {
        switch (event.getActionCommand())
        {
            //  Run The Poll For The Specified Duration
            case "Start":
                //  Ensures the duration field has valid data, disregards if otherwise
                try{
                    int delay = Integer.parseInt(Main.screen.DurationField.getText());
                    Main.screen.Delay = delay;
                    Main.screen.remainingSecondsLabel.setText(delay + "s");
                    Main.screen.VoteRunning = true;
                    Main.screen.timer.scheduleAtFixedRate(new Screen.TimerTick(),0L,1000L);
                    Main.connection.chat.sendMessage(Main.config.Username,"The Poll Has Started");
                    break;
                }
                catch(NumberFormatException ignored){break;}
            //  Reset the poll and duration field
            case "Reset":
                Main.screen.Votes.clear();
                Main.screen.DurationField.setText("0");
                VotesManager.Votes.clear();
                VotesManager.Options.clear();
                VotesManager.Voted_Users.clear();
                break;
            // Add Option To List
//            case "Add Option":
//                if(Main.screen.NameField.getText().isEmpty())
//                {
//                    return;
//                }
//                VotesManager.Votes.add(0);
//                DefaultListModel<String> model = Main.screen.Votes;
//                model.addElement(Main.screen.NameField.getText() + ", 0");
//                Main.screen.NameField.setText("");
//                VotesManager.Options.add(Main.screen.NameField.getText().toLowerCase());
//                break;
//            //  Loads Preset1 from the config and populates votes list
//            case "Preset 1":
//                String Preset1CSV = Main.config.Preset_1;
//                String[] Preset1List = Preset1CSV.split(",");
//                for(String Option : Preset1List)
//                {
//                    DefaultListModel<String> model1 = Main.screen.Votes;
//                    model1.addElement(Option + ", 0");
//                    VotesManager.Votes.add(0);
//                    VotesManager.Options.add(Option.toLowerCase());
//                }
//                break;
//            //  Loads Preset2 from the config and populates votes list
//            case "Preset 2":
//                String Preset2CSV = Main.config.Preset2;
//                String[] Preset2List = Preset2CSV.split(",");
//                for(String Option : Preset2List)
//                {
//                    DefaultListModel<String> model2 = Main.screen.Votes;
//                    model2.addElement(Option + ", 0");
//                    VotesManager.Votes.add(0);
//                    VotesManager.Options.add(Option.toLowerCase());
//                }
//                break;
//            //  Loads Preset3 from the config and populates votes list
//            case "Preset 3":
//                String Preset3CSV = Main.config.Preset3;
//                String[] Preset3List = Preset3CSV.split(",");
//                for(String Option : Preset3List)
//                {
//                    DefaultListModel<String> model3 = Main.screen.Votes;
//                    model3.addElement(Option + ", 0");
//                    VotesManager.Votes.add(0);
//                    VotesManager.Options.add(Option.toLowerCase());
//                }
//                break;
//            case "Generate Random":
//                try{
//                    int Min = 1;
//                    int Max = Integer.parseInt(Main.screen.randomMaxField.getText());
//                    int Value = Main.screen.RNG.nextInt(Min,Max + 1);
//                    JOptionPane.showMessageDialog(null,"Random Value Is: " + Value,"Random Number",JOptionPane.INFORMATION_MESSAGE);
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
//                }
//            case "Send Instruction Message":
//                Main.connection.chat.sendMessage(Main.config.ChannelName,"To vote on the poll please use " + Main.config.votePrefix + " (option name or number)");
//                JOptionPane.showMessageDialog(null,"Message Sent","Action",JOptionPane.INFORMATION_MESSAGE);
//            default:
//                break;
        }
    }
}
