package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.ArrayUtils;

public class StatusCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("Status can not be empty!");
		}
		else
		{
			String status = ArrayUtils.stringify(event.args, " ");

			Discord.discord.setRP(status);

			event.commandSuccess("Setting status to: " + status);
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Set the bot status!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sstatus [what]' to set the bot status!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
