package io.github.glowman554.nudel.discord;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageLogger extends ListenerAdapter
{
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
	{
        super.onMessageReceived(event);
        if (event.isFromType(ChannelType.PRIVATE))
		{
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay());
        }
		else
		{
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(), event.getAuthor().getAsTag(), event.getMessage().getContentDisplay());
        }
    }
}