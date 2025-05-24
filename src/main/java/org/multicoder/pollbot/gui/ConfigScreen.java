package org.multicoder.pollbot.gui;

import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.config.JsonConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConfigScreen extends JFrame implements ActionListener
{
    JPasswordField AccessToken = new JPasswordField();
    JTextField Username = new JTextField();
    JTextField ClientId = new JTextField();
    JTextField VotePrefix = new JTextField();
    JTextField PollCommandPrefix = new JTextField();
    JLabel AccessTokenLabel = new JLabel("Access Token");
    JLabel UsernameLabel = new JLabel("Username");
    JLabel ClientIDLabel = new JLabel("Client ID");
    JLabel VotePrefixLabel = new JLabel("Vote Prefix");
    JLabel PollCommandPrefixLabel = new JLabel("Command Prefix");
    JLabel Preset1Label = new JLabel("Preset 1");
    JLabel Preset2Label = new JLabel("Preset 2");
    JLabel Preset3Label = new JLabel("Preset 3");
    JTextArea Preset1 = new JTextArea();
    JTextArea Preset2 = new JTextArea();
    JTextArea Preset3 = new JTextArea();
    JButton Save = new JButton("Save");
    JButton ShowToken = new JButton("Show/Hide Token");

    public ConfigScreen() throws IOException {
        super("Twitch Poll Bot Config");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(ImageIO.read(Main.ICON));
        setSize(1150,450);
        setLayout(null);
        setFont(new Font("Arial",Font.PLAIN,16));
        SetupComponents();
        setResizable(false);
        setVisible(true);
    }

    private void SetupComponents()
    {
        Username.setBounds(120,10,200,25);
        UsernameLabel.setBounds(10,10,100,25);
        ClientId.setBounds(120,50,200,25);
        ClientIDLabel.setBounds(10,50,100,25);
        AccessToken.setBounds(120,100,200,25);
        AccessTokenLabel.setBounds(10,100,100,25);
        ShowToken.setBounds(330,100,150,25);
        VotePrefix.setBounds(120,150,100,25);
        VotePrefixLabel.setBounds(10,150,100,25);
        PollCommandPrefix.setBounds(120,200,100,25);
        PollCommandPrefixLabel.setBounds(10,200,100,25);
        Preset1.setBounds(500,50,200,280);
        Preset1Label.setBounds(550,10,150,25);
        Preset2.setBounds(710,50,200,280);
        Preset2Label.setBounds(760,10,150,25);
        Preset3.setBounds(920,50,200,280);
        Preset3Label.setBounds(960,10,150,25);
        Save.setBounds(10,250,100,50);
        AddListeners();
        AddComponents();
        LoadConfig();
    }
    private void LoadConfig()
    {
        AccessToken.setEchoChar((char)0);
        JsonConfig Config = Main.config;
        Username.setText(Config.Username);
        ClientId.setText(Config.ClientID);
        VotePrefix.setText(Config.VotePrefix);
        PollCommandPrefix.setText(Config.PollCommandPrefix);
        AccessToken.setText(Config.AccessToken);
        StringBuilder PresetBuilder = new StringBuilder();
        for(String option : Config.Preset_1) PresetBuilder.append(option).append("\n");
        Preset1.setText(PresetBuilder.toString());
        PresetBuilder.delete(0, PresetBuilder.length());
        for(String option : Config.Preset_2) PresetBuilder.append(option).append("\n");
        Preset2.setText(PresetBuilder.toString());
        PresetBuilder.delete(0, PresetBuilder.length());
        for(String option : Config.Preset_3) PresetBuilder.append(option).append("\n");
        Preset3.setText(PresetBuilder.toString());
    }
    private void AddListeners()
    {
        ShowToken.addActionListener(this);
        Save.addActionListener(this);
    }
    private void AddComponents()
    {
        add(Username);
        add(UsernameLabel);
        add(ClientId);
        add(ClientIDLabel);
        add(AccessToken);
        add(AccessTokenLabel);
        add(ShowToken);
        add(VotePrefix);
        add(VotePrefixLabel);
        add(PollCommandPrefix);
        add(PollCommandPrefixLabel);
        add(Preset1);
        add(Preset1Label);
        add(Preset2);
        add(Preset2Label);
        add(Preset3);
        add(Preset3Label);
        add(Save);
    }
    private void UpdateConfig()
    {
        JsonConfig Config = Main.config;
        Config.Username = Username.getText();
        Config.ClientID = ClientId.getText();
        Config.VotePrefix = VotePrefix.getText();
        Config.PollCommandPrefix = PollCommandPrefix.getText();
        char[] AccessTokenChar = AccessToken.getPassword();
        StringBuilder AccessTokenBuilder = new StringBuilder();
        for(char c : AccessTokenChar) AccessTokenBuilder.append(c);
        Config.AccessToken = AccessTokenBuilder.toString();
        String[] Preset_1_list = Preset1.getText().split("\n");
        String[] Preset_2_list = Preset2.getText().split("\n");
        String[] Preset_3_list = Preset3.getText().split("\n");
        Config.Preset_1 = Preset_1_list;
        Config.Preset_2 = Preset_2_list;
        Config.Preset_3 = Preset_3_list;
        try{Config.UpdateConfig();} catch (Exception e) {
            Main.ERRLOG.error("Error Updating Config",e);
            JOptionPane.showMessageDialog(null,"Error Trying To Update Config","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand()){
            case "Show/Hide Token":
                if(AccessToken.getEchoChar() == (char) 0){
                    AccessToken.setEchoChar('*');
                }
                else{
                    AccessToken.setEchoChar((char)0);
                }
                break;
            case "Save":
                UpdateConfig();
                break;
        }
    }
}
