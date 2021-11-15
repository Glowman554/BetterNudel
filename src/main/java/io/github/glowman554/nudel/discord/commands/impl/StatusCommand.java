package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.utils.ArrayUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class StatusCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("Status can not be empty!");
		}
		else
		{
			String status = ArrayUtils.stringify(event.args, " ");

			Discord.discord.setRP(status);

			event.commandSuccess("Setting status to: " + status);
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Set the bot status!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sstatus [what]' to set the bot status!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String status = event.getOption("status").getAsString();

		Discord.discord.setRP(status);

		event.reply("Setting status to: " + status).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("status", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("status", "Status for bot", SlashCommandParameter.STRING, true)
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
