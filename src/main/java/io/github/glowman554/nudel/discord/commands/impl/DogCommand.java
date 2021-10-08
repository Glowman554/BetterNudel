package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;

import io.github.glowman554.nudel.api.DogApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.FileUtils;

public class DogCommand implements Command
{
	
	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("Command takes no arguments");
		}
		else
		{
			DogApi api = new DogApi();
			api.getDog();

			String file = FileUtils.randomTmpFile(FileUtils.getFileExtension(api._url));
			api.download(file);

			event.event.getChannel().sendFile(new File(file)).queue();
		}
		
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "See a cute dog!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sdog' to see a cute cat!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
}
