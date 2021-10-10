package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.api.FactApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class FactCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("This command does not take any arguments.");
		}
		else
		{
			FactApi api = new FactApi();
			event.commandSuccess(api.getFact());
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Get a random fact.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sfact' to get a random fact.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
