package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class NickCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 1)
		{
			event.commandFail("Please specify a new nickname.");
		}
		else
		{
			String nick = event.args[0];

			event.event.getGuild().getSelfMember().modifyNickname(nick).queue();

			event.commandSuccess("Nickname changed to " + nick);
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Set the bot's nickname.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%snick [nickname]' set the bot's nickname!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return "nick";
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String nick = event.getOption("nickname").getAsString();

		event.getGuild().getSelfMember().modifyNickname(nick).queue();

		event.reply("Nickname changed to " + nick).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("nick", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("nickname", "Nickname to set", SlashCommandParameter.STRING, true)
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
