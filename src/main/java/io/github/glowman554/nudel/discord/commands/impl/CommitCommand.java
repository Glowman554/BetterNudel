package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.api.CommitApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class CommitCommand implements Command, SlashCommand
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
							String rp = new CommitApi().getCommit();


							while (rp.length() > 100)
							{
								System.out.println("Not using rp: " + rp);
								rp = new CommitApi().getCommit();
							}

							System.out.println("Setting rp to: " + rp);;

							Discord.discord.setRP(rp);
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}

						try
						{
							Thread.sleep(1000 * 60 * 60);
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

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		CommitApi api = new CommitApi();
		event.reply(api.getCommit()).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("commit", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {});

		try
		{
			reg.doRegister(Discord.discord.token, Discord.discord.application_id);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
