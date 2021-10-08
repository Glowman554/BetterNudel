package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.furrywrapper.FurryBotApi;
import io.github.glowman554.nudel.furrywrapper.FurryResult;
import io.github.glowman554.nudel.utils.ArrayUtils;
import io.github.glowman554.nudel.utils.FileUtils;

public class FurryCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (!(event.args.length == 0 || event.args.length == 1))
		{
			event.commandFail("Invalid arguments");
		}
		else
		{
			String method = "fursuit";
			FurryBotApi api = new FurryBotApi();

			if (event.args.length == 1)
			{
				method = event.args[0];
			}

			method = "furry/" + method;

			if (!ArrayUtils.contains(api._methods, method))
			{
				event.commandFail("Invalid method");
			}
			else
			{
				FurryResult result = api.random_image(method);
				String tmp_file = FileUtils.randomTmpFile(FileUtils.getFileExtension(result.url));

				result.download(tmp_file);

				event.event.getChannel().sendFile(new File(tmp_file)).queue();
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
		return "See a random furry image";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sfurry [method?]' to see something furry related!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
	
}
