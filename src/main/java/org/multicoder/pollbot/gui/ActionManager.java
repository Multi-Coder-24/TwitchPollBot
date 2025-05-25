package org.multicoder.pollbot.gui;

import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.util.VotesManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ActionManager
{
    public static void HandleAction(ActionEvent event)
    {
        if(Main.screen.connection == null){
            return;
        }
        switch (event.getActionCommand())
        {
            //  Run The Poll For The Specified Duration
            case "Start":
                //  Ensures the duration field has valid data, disregards if otherwise
                try{
                    int delay = Integer.parseInt(Main.screen.MainApp.DurationField.getText());
                    Main.screen.Delay = delay;
                    Main.screen.MainApp.remainingSecondsLabel.setText(delay + "s");
                    Main.screen.VoteRunning = true;
                    Main.screen.timer.scheduleAtFixedRate(new Screen.TimerTick(),0L,1000L);
                    Main.screen.connection.chat.sendMessage(Main.screen.config.Username,"The Poll Has Started");
                    break;
                }
                catch(NumberFormatException ignored){break;}
            //  Reset the poll and duration field
            case "Reset":
                Main.screen.MainApp.Votes.clear();
                Main.screen.MainApp.DurationField.setText("0");
                VotesManager.Votes.clear();
                VotesManager.Options.clear();
                VotesManager.Voted_Users.clear();
                break;
            // Add Option To List
            case "Add Option":
                if(Main.screen.MainApp.NameField.getText().isEmpty())
                {
                    return;
                }
                VotesManager.Votes.add(0);
                DefaultListModel<String> model = Main.screen.MainApp.Votes;
                model.addElement(Main.screen.MainApp.NameField.getText() + ", 0");
                Main.screen.MainApp.NameField.setText("");
                VotesManager.Options.add(Main.screen.MainApp.NameField.getText().toLowerCase());
                break;
            //  Loads Preset1 from the config and populates votes list
            case "Preset 1":
                String[] Preset1List = Main.screen.config.Preset_1;
                for(String Option : Preset1List)
                {
                    DefaultListModel<String> model1 = Main.screen.MainApp.Votes;
                    model1.addElement(Option + ", 0");
                    VotesManager.Votes.add(0);
                    VotesManager.Options.add(Option.toLowerCase());
                    Main.screen.validate();
                }
                break;
            //  Loads Preset2 from the config and populates votes list
            case "Preset 2":
                String[] Preset2List = Main.screen.config.Preset_2;
                for(String Option : Preset2List)
                {
                    DefaultListModel<String> model2 = Main.screen.MainApp.Votes;
                    model2.addElement(Option + ", 0");
                    VotesManager.Votes.add(0);
                    VotesManager.Options.add(Option.toLowerCase());
                    Main.screen.validate();
                }
                break;
            //  Loads Preset3 from the config and populates votes list
            case "Preset 3":
                String[] Preset3List = Main.screen.config.Preset_3;
                for(String Option : Preset3List)
                {
                    DefaultListModel<String> model3 = Main.screen.MainApp.Votes;
                    model3.addElement(Option + ", 0");
                    VotesManager.Votes.add(0);
                    VotesManager.Options.add(Option.toLowerCase());
                    Main.screen.validate();
                }
                break;
            case "Generate Random":
                try{
                    int Min = 1;
                    int Max = Integer.parseInt(Main.screen.MainApp.randomMaxField.getText());
                    int Value = Main.screen.RNG.nextInt(Min,Max + 1);
                    JOptionPane.showMessageDialog(null,"Random Value Is: " + Value,"Random Number",JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Error Generating Random Number","Error",JOptionPane.ERROR_MESSAGE);
                    Main.ERRLOG.error("Error Generating Random Number",e);
                }
                break;
            case "Send Instruction Message":
                Main.screen.connection.chat.sendMessage(Main.screen.config.Username,"To vote on the poll please use " + Main.screen.config.VotePrefix + " (option name or number)");
                JOptionPane.showMessageDialog(null,"Message Sent","Action",JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                break;
        }
    }
}
