package org.multicoder.pollbot.gui;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.twitch.VotesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Screen extends JFrame implements ActionListener
{
    //  Component Properties
    public DefaultListModel<String> Votes = new DefaultListModel<>();
    public JList<String> VoteList = new JList<>(Votes);
    public JButton AddOptionButton = new JButton("Add Option");
    public JButton StartButton = new JButton("Start");
    public JButton ResetButton = new JButton("Reset");
    public JButton Preset1 = new JButton("Preset 1");
    public JButton Preset2 = new JButton("Preset 2");
    public JButton Preset3 = new JButton("Preset 3");
    public JButton RandomButton = new JButton("Generate Random");
    public JTextField DurationField = new JTextField("0");
    public JTextField NameField = new JTextField();
    public JTextField randomMaxField = new JTextField();
    public JLabel votesLabel = new JLabel("Votes");
    public JLabel nameLabel = new JLabel("Name:");
    public JLabel durationLabel = new JLabel("Duration:");
    public JLabel remainingLabel = new JLabel("Remaining:");
    public JLabel remainingSecondsLabel = new JLabel("0s");
    public JLabel controlsLabel = new JLabel("Poll Controls");
    public JLabel randomLabel = new JLabel("RNG Controls");
    public JLabel randomMinLabel = new JLabel("From: 1");
    public JLabel randomMaxLabel = new JLabel("To");

    //  Non Component Properties
    public Timer timer = new Timer();
    public int Delay = 0;
    public boolean VoteRunning = false;
    public Random RNG;


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
                Main.connection.chat.sendMessage(Main.config.ChannelName,"TwitchPollBot is disconnecting");
                Main.connection.chat.disconnect();
                Main.connection.chat.close();
                Main.connection.client.close();
                System.exit(0);
            }
        });
        SetupComponents();
        RNG = new Random();
        setResizable(false);
        setVisible(true);
    }
    //  Helper method that sets the bounds and calls the other 2 helpers
    private void SetupComponents()
    {
        VoteList.setBounds(425,100,300,300);
        votesLabel.setBounds(550,0,75,100);
        nameLabel.setBounds(10,70,50,25);
        NameField.setBounds(90,70,100,25);
        AddOptionButton.setBounds(195,70,100,25);
        durationLabel.setBounds(10,110,75,25);
        DurationField.setBounds(90,110,100,25);
        StartButton.setBounds(195,110,100,25);
        ResetButton.setBounds(300,110,100,25);
        remainingLabel.setBounds(85,150,75,25);
        remainingSecondsLabel.setBounds(170,150,75,25);
        Preset1.setBounds(10,190,100,25);
        Preset2.setBounds(115,190,100,25);
        Preset3.setBounds(220,190,100,25);
        controlsLabel.setBounds(100,30,100,25);
        randomLabel.setBounds(100,230,100,25);
        randomMinLabel.setBounds(10,270,100,25);
        randomMaxLabel.setBounds(10,310,40,25);
        randomMaxField.setBounds(60,310,100,25);
        RandomButton.setBounds(10,350,150,25);
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
        RandomButton.addActionListener(this);
    }
    //  Helper method that adds all components
    private void AddComponents()
    {
        add(VoteList);
        add(votesLabel);
        add(nameLabel);
        add(NameField);
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
        add(controlsLabel);
        add(randomLabel);
        add(randomMinLabel);
        add(randomMaxLabel);
        add(randomMaxField);
        add(RandomButton);
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
                if(Message.toLowerCase().startsWith(Main.config.votePrefix))
                {
                    VotesManager.vote_trigger(Message,Username);
                }
            }
        }
    }
}
