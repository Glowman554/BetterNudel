package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class CoinflipCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("To many arguments");
			return;
		}

		boolean random = Math.random() < 0.5;
		
		if (random)
		{
			event.commandSuccess("You've landed on heads!");
		}
		else
		{
			event.commandSuccess("You've landed on tails!");
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Flip a coin!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%scoinflip' to flip a coin!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		boolean random = Math.random() < 0.5;
		
		if (random)
		{
			event.reply("You've landed on heads!").queue();
		}
		else
		{
			event.reply("You've landed on tails!").queue();
		}
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("coinflip", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {});

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
