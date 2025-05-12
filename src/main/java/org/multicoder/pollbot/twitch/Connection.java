package org.multicoder.pollbot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.gui.Screen;

import javax.swing.*;

public class Connection
{
    //  Main Constructor
    public Connection()
    {
        try{
            //  Creates the twitch bot Client using the provided values from config
            TwitchClient client = TwitchClientBuilder.builder().withUserAgent("TwitchPollBot. By Multicoder").withChatAccount(new OAuth2Credential("twitch",Main.config.ClientSecret)).withClientId(Main.config.ClientID).withEnableChat(true).build();
            //  Gets the users twitch chat
            TwitchChat chat = client.getChat();
            //  Connects to the users twitch chat
            chat.connect();
            //  Sends a ready message in the twitch chat
            chat.sendMessage(Main.config.ChannelName,"TwitchPollBot Connected");
            //  Adds the Message Handler from Screen into the twitch bot event manager
            chat.getEventManager().onEvent(ChannelMessageEvent.class, Screen.MessageEvents::ChatMessage);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(Main.screen.Votes,e.getMessage(),"Critical Error",JOptionPane.ERROR_MESSAGE);
        }

    }
}
