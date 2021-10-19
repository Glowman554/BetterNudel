package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.api.CommitApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class CommitCommand implements Command
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
			CommitApi api = new CommitApi();
			event.commandSuccess(api.getCommit());
		}
		
	}

	@Override
	public void on_register()
	{
		if (Main.parser.is_option("--random-rp"))
		{
			new Thread() {
				public void run()
				{
					while (true)
					{
						try
						{
							Discord.discord.setRP(new CommitApi().getCommit());
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}

						try
						{
							Thread.sleep(1000);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
							return;
						}
					}
				};
			}.start();
		}
	}

	@Override
	public String get_short_help()
	{
		return "Get a random commit message!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%scommit' to get a random commit message!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}
}
