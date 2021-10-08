package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class CoinflipCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("To many arguments");
			return;
		}

		boolean random = Math.random() < 0.5;
		
		if (random)
		{
			event.commandSuccess("You've landed on heads!");
		}
		else
		{
			event.commandSuccess("You've landed on tails!");
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Flip a coin!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%scoinflip' to flip a coin!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
