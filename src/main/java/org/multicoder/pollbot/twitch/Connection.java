package org.multicoder.pollbot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import org.multicoder.pollbot.Main;
import org.multicoder.pollbot.gui.Screen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Connection
{
    public TwitchClient client;
    public TwitchChat chat;
    public List<String> moderators;
    //  Main Constructor
    public Connection(Screen inst)
    {
        try{
            //  Creates the twitch bot Client using the provided values from config
            client = TwitchClientBuilder.builder().withUserAgent("TwitchPollBot. By Multicoder").withChatAccount(new OAuth2Credential("twitch",inst.config.AccessToken)).withClientId(inst.config.ClientID).withEnableChat(true).withEnableHelix(true).build();
            //  Gets the users twitch chat
            chat = client.getChat();
            //  Connects to the users twitch chat
            chat.connect();
            //  Sends a ready message in the twitch chat
            chat.sendMessage(inst.config.Username,"TwitchPollBot Connected");
            //  Adds the Message Handler from Screen into the twitch bot event manager
            chat.getEventManager().onEvent(ChannelMessageEvent.class, Screen.MessageEvents::ChatMessage);
            //  Retrieve the moderators
            moderators = new ArrayList<>();
            client.getHelix().getModerators(inst.config.AccessToken,inst.config.BroadcasterID,null,null,null).execute().getModerators().forEach(moderator -> moderators.add(moderator.getUserName()));

        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error Connecting Twitch API","Critical Error",JOptionPane.ERROR_MESSAGE);
            Main.ERRLOG.error("Error Connecting Twitch API",e);
        }

    }
}
