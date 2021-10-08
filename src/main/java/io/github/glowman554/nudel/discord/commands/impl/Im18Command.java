package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class Im18Command implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		Discord.discord.commandManager.permissionManager.addPermission(event.event.getAuthor().getId(), "im_18");

		event.commandSuccess("You are now in the 18+ role!");
	}

	@Override
	public void on_register()
	{
	}

	@Override
	public String get_short_help()
	{
		return "Gives the 18+ role to the user";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sim_18' to get the 18+ role", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
