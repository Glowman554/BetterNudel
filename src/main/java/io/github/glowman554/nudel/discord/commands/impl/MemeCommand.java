package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;

import io.github.glowman554.nudel.api.MemeApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.FileUtils;

public class MemeCommand implements Command
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
			MemeApi api = new MemeApi();
			api.getMeme();

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
		return "See memes!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%smeme' to see memes!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
