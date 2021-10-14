package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;

import io.github.glowman554.nudel.api.GoogleTtsApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.ArrayUtils;
import io.github.glowman554.nudel.utils.FileUtils;

public class TtsCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("No text specified");
		}
		else
		{
			String text = ArrayUtils.stringify(event.args, " ");

			GoogleTtsApi api = new GoogleTtsApi();
			api.setLang("en");
			api.getTts(text);

			String path = FileUtils.randomTmpFile("mp3");

			api.download(path);

			event.event.getChannel().sendFile(new File(path)).queue();
		}
	}

	@Override
	public void on_register() 
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Sends a mp3 file of the text specified";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%stts [text]' to send a mp3 file of the text specified", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}