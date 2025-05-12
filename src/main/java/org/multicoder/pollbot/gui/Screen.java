package org.multicoder.pollbot.gui;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.twitch.VotesManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Screen extends Frame implements ActionListener
{
    //  Component Properties
    public List Votes = new List();
    public Label optionLabel = new Label("Name:");
    public TextField OptionField = new TextField();
    public Button AddOptionButton = new Button("Add Option");
    public Button StartButton = new Button("Start");
    public TextField DurationField = new TextField("0");
    public Label durationLabel = new Label("Duration:");
    public Label remainingLabel = new Label("Remaining:");
    public Label remainingSecondsLabel = new Label("0s");
    public Button ResetButton = new Button("Reset");
    public Button Preset1 = new Button("Preset 1");
    public Button Preset2 = new Button("Preset 2");
    public Button Preset3 = new Button("Preset 3");

    //  Non Component Properties
    public Timer timer = new Timer();
    public int Delay = 0;
    public boolean VoteRunning = false;


    //  Main Constructor
    public Screen()
    {
        super("Twitch Poll Bot");
        setSize(650,500);
        setLayout(null);
        setFont(new Font("Arial",Font.PLAIN,16));
        addWindowListener(new WindowAdapter() {@Override public void windowClosing(WindowEvent e) {super.windowClosing(e);System.exit(0);}});
        SetupComponents();
        setResizable(false);
        setVisible(true);
    }
    //  Helper method that sets the bounds and calls the other 2 helpers
    private void SetupComponents()
    {
        Votes.setBounds(300,50,300,400);
        optionLabel.setBounds(10,50,75,25);
        OptionField.setBounds(90,50,100,25);
        AddOptionButton.setBounds(10,90,100,25);
        durationLabel.setBounds(10,150,75,25);
        DurationField.setBounds(90,150,100,25);
        StartButton.setBounds(10,200,100,25);
        remainingLabel.setBounds(10,300,75,25);
        remainingSecondsLabel.setBounds(90,300,100,25);
        ResetButton.setBounds(10,350,100,25);
        Preset1.setBounds(10,460,100,25);
        Preset2.setBounds(120,460,100,25);
        Preset3.setBounds(240,460,100,25);
        AddListeners();
        AddComponents();
    }
    //  Helper method that adds all button action listeners
    private void AddListeners(){
        AddOptionButton.addActionListener(this);
        StartButton.addActionListener(this);
        ResetButton.addActionListener(this);
        Preset1.addActionListener(this);
        Preset2.addActionListener(this);
        Preset3.addActionListener(this);
    }
    //  Helper method that adds all components
    private void AddComponents()
    {
        add(Votes);
        add(optionLabel);
        add(OptionField);
        add(AddOptionButton);
        add(StartButton);
        add(DurationField);
        add(durationLabel);
        add(remainingLabel);
        add(remainingSecondsLabel);
        add(ResetButton);
        add(Preset1);
        add(Preset2);
        add(Preset3);
    }

    //  Button Handler
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Add Option To List
        if(e.getSource() == AddOptionButton)
        {
            if(OptionField.getText().isEmpty()){
                return;
            }
            VotesManager.Votes.add(0);
            Votes.add(OptionField.getText() + ", 0");
            OptionField.setText("");
        }
        //  Run The Poll For The Specified Duration
        else if(e.getSource() == StartButton)
        {
            //  Ensures the duration field has valid data, disregards if otherwise
            try{
                int delay = Integer.parseInt(DurationField.getText());
                Delay = delay;
                remainingSecondsLabel.setText(delay + "s");
                VoteRunning = true;
                timer.scheduleAtFixedRate(new TimerTick(),0L,1000L);
            }
            catch(NumberFormatException ignored){}
        }
        //  Reset the poll and duration field
        else if(e.getSource() == ResetButton)
        {
            Votes.removeAll();
            DurationField.setText("0");
        }
        //  Loads Preset1 from the config and populates votes list
        else if(e.getSource() == Preset1)
        {
            String Preset1CSV = Main.config.Preset1;
            String[] Preset1List = Preset1CSV.split(",");
            for(String Option : Preset1List)
            {
                Votes.add(Option + ", 0");
            }
        }
        //  Loads Preset2 from the config and populates votes list
        else if(e.getSource() == Preset2)
        {
            String Preset2CSV = Main.config.Preset2;
            String[] Preset2List = Preset2CSV.split(",");
            for(String Option : Preset2List)
            {
                Votes.add(Option + ", 0");
            }
        }
        //  Loads Preset3 from the config and populates votes list
        else if(e.getSource() == Preset3)
        {
            String Preset3CSV = Main.config.Preset3;
            String[] Preset3List = Preset3CSV.split(",");
            for(String Option : Preset3List)
            {
                Votes.add(Option + ", 0");
            }
        }

    }

    //  Timer Task responsible for the duration and handles the result fetching from the list
    public static class TimerTick extends TimerTask
    {

        @Override
        public void run()
        {
            if(Main.screen.Delay > 0)
            {
                Main.screen.Delay--;
                Main.screen.remainingSecondsLabel.setText(Main.screen.Delay + "s");
            }
            else
            {
                Main.screen.timer.cancel();
                Main.screen.remainingSecondsLabel.setText("0s");
                Main.screen.VoteRunning = false;
                VotesManager.FetchWinner();
            }

        }
    }
    //  Message Handler responsible for reading messages and checking if the vote command is used
    public static class MessageEvents
    {

        public static void ChatMessage(ChannelMessageEvent channelMessageEvent)
        {
            if(Main.screen.VoteRunning)
            {
                String Message = channelMessageEvent.getMessage();
                String Username = channelMessageEvent.getUser().getName();
                if(Message.startsWith("!vote")){
                    VotesManager.vote_trigger(Message,Username);
                }
            }
        }
    }
}
