package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.api.PronounDbApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class PronounDbCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		MessageChannel channel = event.event.getChannel();		
		Message message = channel.retrieveMessageById(event.event.getMessageId()).complete();

		if (event.args.length != 1 || message.getMentionedUsers().size() != 1)
		{
			event.commandFail("Please mention a user to get their pronouns.");
		}
		else
		{
			String user_id = message.getMentionedUsers().get(0).getId();

			PronounDbApi api = new PronounDbApi();
			api.fetchPronoun(user_id);

			event.commandSuccess(String.format("The pronouns of %s are %s!", event.args[0], api.translatePronoun()));
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Get the pronouns of a user.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%spronoun [@user]' to get the pronouns of a user.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String user_id = event.getOption("user").getAsString();

		PronounDbApi api = new PronounDbApi();
		api.fetchPronoun(user_id);

		event.reply(String.format("The pronouns of %s are %s!", event.getOption("user").getAsUser().getAsMention(), api.translatePronoun())).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("pronoun", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("user", "User to fetch pronouns from", SlashCommandParameter.USER, true)
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
