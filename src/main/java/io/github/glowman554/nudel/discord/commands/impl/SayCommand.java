package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.ArrayUtils;

public class SayCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("You need to specify a message to say.");
		}
		else
		{
			String message = ArrayUtils.stringify(event.args, " ");

			event.event.getChannel().sendMessage(message).queue();
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Says a message.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%ssay [what]' to say something!", Discord.discord.commandManager.prefix); 
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
