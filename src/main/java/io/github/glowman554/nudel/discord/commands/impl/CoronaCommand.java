package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.api.CoronaApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class CoronaCommand implements Command, SlashCommand
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
		
	}

	@Override
	public String get_short_help()
	{
		return "Get the latest corona stats for a country";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%scorona [country]' to get the latest corona stats for a country", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String country = event.getOption("country").getAsString();

		CoronaApi api = new CoronaApi();
		CoronaApi.CoronaApiResult result = api.fetchCountry(country);

		event.reply(result.toString()).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("corona", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("country", "The country to fetch data from", SlashCommandParameter.STRING, true, CoronaApi.countries)
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
