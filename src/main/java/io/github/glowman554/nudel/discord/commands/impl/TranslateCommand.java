package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.api.TranslateApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.utils.ArrayUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TranslateCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length < 2)
		{
			event.commandFail(Discord.discord.commandManager.prefix + "translate [language][text]");
			return;
		}
		else
		{
			String language = event.args[0];
			String text = ArrayUtils.stringify(ArrayUtils.remove(event.args, language), " ");

			TranslateApi api = new TranslateApi();
			String response = api.translate(text, language);

			event.commandSuccess(response);
		}
	}

	@Override
	public void on_register()
	{
	}

	@Override
	public String get_short_help()
	{
		return "Translate a text!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%stranslate [language][text]' to translate a text!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String language = event.getOption("language").getAsString();
		String text = event.getOption("text").getAsString();

		TranslateApi api = new TranslateApi();
		String response = api.translate(text, language);

		event.reply(response).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("translate", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("language", "Language to translate to", SlashCommandParameter.STRING, true, TranslateApi.languages),
			new SlashCommandParameter("text", "Text to translate", SlashCommandParameter.STRING, true)
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
