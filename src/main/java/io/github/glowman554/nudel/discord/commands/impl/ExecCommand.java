package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.exec.ExecutionEngine;
import io.github.glowman554.nudel.utils.ArrayUtils;

public class ExecCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("Please specify a command to execute.");
		}
		else
		{
			String command = ArrayUtils.stringify(event.args, " ");

			ExecutionEngine engine = new ExecutionEngine(!Main.parser.is_option("--allow-unsafe-exec"));

			event.commandSuccess(engine.execute(command));
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Execute a shell command.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sexec [command]' to execute a shell command.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return "exec";
	}
	
}
