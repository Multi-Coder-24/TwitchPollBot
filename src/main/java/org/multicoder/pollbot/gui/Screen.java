package org.multicoder.pollbot.gui;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.twitch.VotesManager;

import javax.imageio.ImageIO;
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
    public Button AddOptionButton = new Button("Add Option");
    public Button StartButton = new Button("Start");
    public Button ResetButton = new Button("Reset");
    public Button Preset1 = new Button("Preset 1");
    public Button Preset2 = new Button("Preset 2");
    public Button Preset3 = new Button("Preset 3");
    public TextField DurationField = new TextField("0");
    public TextField OptionField = new TextField();
    public Label votesLabel = new Label("Votes");
    public Label optionLabel = new Label("Name:");
    public Label durationLabel = new Label("Duration:");
    public Label remainingLabel = new Label("Remaining:");
    public Label remainingSecondsLabel = new Label("0s");

    //  Non Component Properties
    public Timer timer = new Timer();
    public int Delay = 0;
    public boolean VoteRunning = false;


    //  Main Constructor
    public Screen() throws Exception
    {
        super("Twitch Poll Bot");
        setIconImage(ImageIO.read(Main.ICON));
        setSize(750,450);
        setLayout(null);
        setFont(new Font("Arial",Font.PLAIN,16));
        addWindowListener(new WindowAdapter()
        {
            @Override public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                //  Closes the twitch api before closing
                Main.connection.chat.disconnect();
                Main.connection.chat.close();
                Main.connection.client.close();
                System.exit(0);
            }
        });
        SetupComponents();
        setResizable(false);
        setVisible(true);
    }
    //  Helper method that sets the bounds and calls the other 2 helpers
    private void SetupComponents()
    {
        Votes.setBounds(425,120,300,300);
        votesLabel.setBounds(550,50,75,100);
        optionLabel.setBounds(10,100,50,25);
        OptionField.setBounds(90,100,100,25);
        AddOptionButton.setBounds(195,100,100,25);
        durationLabel.setBounds(10,150,75,25);
        DurationField.setBounds(90,150,100,25);
        StartButton.setBounds(195,150,100,25);
        ResetButton.setBounds(300,150,100,25);
        remainingLabel.setBounds(90,180,75,25);
        remainingSecondsLabel.setBounds(170,180,75,25);
        Preset1.setBounds(10,210,100,25);
        Preset2.setBounds(115,210,100,25);
        Preset3.setBounds(220,210,100,25);
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
        add(votesLabel);
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
        ActionManager.HandleAction(e);
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
                Main.connection.chat.sendMessage(Main.config.ChannelName,"The Poll Has Ended\nThe Winning Option will be announced");
                Main.screen.timer.cancel();
                Main.screen.remainingSecondsLabel.setText("0s");
                Main.screen.VoteRunning = false;
                VotesManager.FetchWinner();
                Main.screen.timer = new Timer();
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
                if(Message.startsWith("!vote"))
                {
                    VotesManager.vote_trigger(Message,Username);
                }
            }
        }
    }
}
