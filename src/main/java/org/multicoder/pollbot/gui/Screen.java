package org.multicoder.pollbot.gui;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.config.JsonConfig;
import org.multicoder.pollbot.twitch.Connection;
import org.multicoder.pollbot.twitch.CommandManager;
import org.multicoder.pollbot.util.VotesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;


public class Screen extends JFrame implements ActionListener
{
    //  Components
    public PollPanel MainApp = new PollPanel();
    public ConfigPanel ConfigPanel = new ConfigPanel();
    public JButton Poll = new JButton("Poll");
    public JButton Config = new JButton("Config");

    //  Non Component
    public Timer timer = new Timer();
    public int Delay = 0;
    public boolean VoteRunning = false;
    public Random RNG;
    public Connection connection;
    public JsonConfig config;


    //  Main Constructor
    public Screen() throws Exception
    {
        super("Twitch Poll Bot");
        Main.LOG.info("UI Initialization");
        setIconImage(ImageIO.read(Main.ICON));
        setSize(1150,500);
        setLayout(null);
        setFont(new Font("Arial",Font.PLAIN,16));
        Main.LOG.info("Add Screen Close Hook");
        addWindowListener(new WindowAdapter()
        {
            @Override public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                //  Closes the twitch api before closing
                if(connection == null) System.exit(0);
                connection.chat.sendMessage(config.Username,"TwitchPollBot is disconnecting");
                try {Thread.sleep(200);} catch (InterruptedException ignored) {}
                connection.chat.disconnect();
                connection.chat.close();
                connection.client.close();
                System.exit(0);
            }

            @Override
            public void windowGainedFocus(WindowEvent e)
            {
                super.windowGainedFocus(e);
                validate();
            }
        });
        Main.LOG.info("Setting Up Components");
        SetupComponents();
        setResizable(false);
        setVisible(true);
        Main.LOG.info("Loading Config");
        config = new JsonConfig();
        if(config.Username.equals("changeme")) {
            Main.LOG.info("New Config, Loading ConfigPanel App Instead of PollPanel");
            Main.LOG.info("No Twitch Connection Will Be Initiated Until Requested By ConfigPanel");
            Poll.setEnabled(true);
            Config.setEnabled(false);
            MainApp.setVisible(false);
            ConfigPanel.setVisible(true);
            ConfigPanel.LoadConfig();
        }
        else
        {
            Main.LOG.info("Starting Twitch API Connection");
            Poll.setEnabled(false);
            connection = new Connection(this);
        }
        Main.LOG.info("Initiating Random Number Generator");
        RNG = new Random();
        Main.LOG.info("UI Initialized");
    }

    private void SetupComponents()
    {
        MainApp.setBounds(0,50,750,500);
        ConfigPanel.setBounds(0,50,1150,450);
        Poll.setBounds(50,0,100,25);
        Config.setBounds(200,0,100,25);
        add(MainApp);
        add(Poll);
        add(Config);
        add(ConfigPanel);
        Poll.addActionListener(this);
        Config.addActionListener(this);
    }
    //  Button Handler
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(Poll)){
            ConfigPanel.setVisible(false);
            MainApp.setVisible(true);
            Config.setEnabled(true);
            Poll.setEnabled(false);
        }
        else if(e.getSource().equals(Config)){
            ConfigPanel.setVisible(true);
            MainApp.setVisible(false);
            Config.setEnabled(false);
            Poll.setEnabled(true);
            ConfigPanel.LoadConfig();
        }
        else if(e.getActionCommand().equals("Start Twitch API"))
        {
            Main.LOG.info("Twitch API Start Requested");
            connection = new Connection(this);
        }
        ActionManager.HandleAction(e);
    }

    //Timer Task responsible for the duration and handles the result fetching from the list
    public static class TimerTick extends TimerTask
    {

        @Override
        public void run()
        {
            if(Main.screen.Delay > 0)
            {
                Main.screen.Delay--;
                Main.screen.MainApp.remainingSecondsLabel.setText(Main.screen.Delay + "s");
            }
            else
            {
                Main.screen.connection.chat.sendMessage(Main.screen.config.Username,"The Poll Has Ended\nThe Winning Option will be announced");
                Main.screen.timer.cancel();
                Main.screen.MainApp.remainingSecondsLabel.setText("0s");
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
                if(Message.toLowerCase().startsWith(Main.screen.config.VotePrefix))
                {
                    VotesManager.vote_trigger(Message,Username);
                }
                else if(Message.toLowerCase().startsWith(Main.screen.config.PollCommandPrefix)){
                    CommandManager.HandleCommand(Username,Message);
                }
            }
            else{
                String Message = channelMessageEvent.getMessage();
                String Username = channelMessageEvent.getUser().getName();
                if(Message.toLowerCase().startsWith(Main.screen.config.PollCommandPrefix)) {
                    CommandManager.HandleCommand(Username,Message);
                }
            }
        }
    }
}
