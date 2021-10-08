package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class PingCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		event.event.getChannel().sendMessage("Pong!").queue();
	}

	@Override
	public void on_register()
	{
	}

	@Override
	public String get_short_help()
	{
		return "Ping the bot!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sping' to ping the bot!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
