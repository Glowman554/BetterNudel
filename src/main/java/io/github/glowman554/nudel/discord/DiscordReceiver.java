package io.github.glowman554.nudel.discord;

import io.github.glowman554.nudel.discord.commands.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordReceiver extends ListenerAdapter
{
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		super.onMessageReceived(event);

		if (event.getAuthor().isBot())
		{
			return;
		}

		CommandEvent commandEvent = new CommandEvent(event.getMessage().getContentRaw(), event.getMessage().getContentRaw().split(" ")[0], CommandEvent.getArguments(event.getMessage().getContentRaw().split(" ")), event);

		try
		{
			Discord.discord.commandManager.onCommand(commandEvent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			commandEvent.commandFail(e.getMessage());
		}
	}
}
