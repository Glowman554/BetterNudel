package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.api.JokeApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class JokeCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("This command doesn't take any arguments.");
		}
		else
		{
			JokeApi api = new JokeApi();
			event.commandSuccess(api.getJoke());
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Get a random joke.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sjoke' to get a random joke.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
