package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class Im18Command implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		Discord.discord.commandManager.permissionManager.addPermission(event.event.getAuthor().getId(), "im_18");

		event.commandSuccess("You are now in the 18+ role!");
	}

	@Override
	public void on_register()
	{
	}

	@Override
	public String get_short_help()
	{
		return "Gives the 18+ role to the user";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sim_18' to get the 18+ role", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		Discord.discord.commandManager.permissionManager.addPermission(event.getUser().getId(), "im_18");
		event.reply("You are now in the 18+ role!").queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("im_18", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {});

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
