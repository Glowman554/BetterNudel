package io.github.glowman554.nudel.discord;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
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

	@Override
	public void onSlashCommand(SlashCommandEvent event)
	{
		super.onSlashCommand(event);
		if (event.isFromGuild())
		{
			System.out.printf("[%s][%s] %s: /%s\n", event.getGuild().getName(), event.getTextChannel().getName(), event.getUser().getAsTag(), event.getName());
		}
		else
		{
			System.out.printf("[PM] %s: /%s\n", event.getUser().getAsTag(), event.getName());
		}

		// event.reply("content").queue();
		// event.getChannel().sendMessage("followup").queue();
	}
}