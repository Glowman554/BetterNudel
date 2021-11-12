package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.ArrayUtils;

public class RepeatCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length < 2)
		{
			event.commandFail("Not enough arguments");
		}
		else
		{
			int count = Integer.parseInt(event.args[0]);

			String[] args = CommandEvent.getArguments(event.args);

			if (!args[0].startsWith(Discord.discord.commandManager.prefix))
			{
				args[0] = Discord.discord.commandManager.prefix + args[0];
			}

			if (args[0].equals(Discord.discord.commandManager.prefix + "repeat"))
			{
				event.commandFail("You can't repeat a repeat command");
			}
			else
			{
				if (count < 11 || Discord.discord.commandManager.permissionManager.hasPermission(event.event.getAuthor().getId(), "no_limit"))
				{
					
					if (count < 0)
					{
						event.commandFail("Count must be greater than 0");
					}
					else
					{
						CommandEvent newEvent = new CommandEvent(ArrayUtils.stringify(args, " "), args[0], CommandEvent.getArguments(args), event.event);

						for (int i = 0; i < count; i++)
						{
							Discord.discord.commandManager.onCommand(newEvent);
						}
					}
				}
				else
				{
					event.commandFail("Count must be less than 10");
				}
			}
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Repeat a command!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%srepeat [count][command]' to repeat a command!", "repeat 10 furry", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
