package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.api.CoronaApi;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class CoronaCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 1)
		{
			event.commandFail("Please specify one country");
		}
		else
		{
			String country = event.args[0];

			CoronaApi api = new CoronaApi();
			CoronaApi.CoronaApiResult result = api.fetchCountry(country);

			event.commandSuccess(result.toString());
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
