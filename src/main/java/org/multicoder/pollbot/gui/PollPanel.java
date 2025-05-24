package org.multicoder.pollbot.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PollPanel extends JPanel implements ActionListener
{
    public DefaultListModel<String> Votes = new DefaultListModel<>();
    public JList<String> VoteList = new JList<>(Votes);
    public JButton AddOptionButton = new JButton("Add Option");
    public JButton StartButton = new JButton("Start");
    public JButton ResetButton = new JButton("Reset");
    public JButton Preset1 = new JButton("Preset 1");
    public JButton Preset2 = new JButton("Preset 2");
    public JButton Preset3 = new JButton("Preset 3");
    public JButton RandomButton = new JButton("Generate Random");
    public JButton HowTo = new JButton("Send Instruction Message");
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


    public PollPanel()
    {
        super(null);
        setSize(750,450);
        setFont(new Font("Arial",Font.PLAIN,16));
        SetupComponents();
        setVisible(true);
    }

    private void SetupComponents() {
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
        HowTo.setBounds(200,350,200,25);
        AddListeners();
        AddComponents();
    }

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
        add(HowTo);
    }
    private void AddListeners(){
        AddOptionButton.addActionListener(this);
        StartButton.addActionListener(this);
        ResetButton.addActionListener(this);
        Preset1.addActionListener(this);
        Preset2.addActionListener(this);
        Preset3.addActionListener(this);
        RandomButton.addActionListener(this);
        HowTo.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Screen Parent = (Screen) getRootPane().getParent();
        Parent.actionPerformed(e);
    }
}
