package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.api.WikipediaApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.utils.ArrayUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class WikipediaCommand implements Command, SlashCommand
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
		
	}

	@Override
	public String get_short_help()
	{
		return "Searches Wikipedia for a given query";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%swikipedia [query]' to search Wikipedia for a given query", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String query = event.getOption("query").getAsString();

		WikipediaApi api = new WikipediaApi();

		event.reply(api.searchWiki(query)).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("wikipedia", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("query", "Query to search", SlashCommandParameter.STRING, true)
		});

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
