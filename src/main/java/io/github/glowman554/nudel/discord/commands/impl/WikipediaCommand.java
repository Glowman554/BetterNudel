package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.api.WikipediaApi;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.ArrayUtils;

public class WikipediaCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("Invalid arguments");
		}
		else
		{
			WikipediaApi api = new WikipediaApi();

			event.commandSuccess(api.searchWiki(ArrayUtils.stringify(event.args, " ")));
		}
	}

	@Override
	public void on_register()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String get_short_help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_long_help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_permission()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
