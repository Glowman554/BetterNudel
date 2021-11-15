package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.api.JokeApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class JokeCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("This command doesn't take any arguments.");
		}
		else
		{
			JokeApi api = new JokeApi();
			event.commandSuccess(api.getJoke());
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Get a random joke.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sjoke' to get a random joke.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		JokeApi api = new JokeApi();
		event.reply(api.getJoke()).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("joke", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {});

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
