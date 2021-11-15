package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;
import java.io.IOException;

import io.github.glowman554.nudel.api.GoogleTtsApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.utils.ArrayUtils;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class TtsCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("No text specified");
		}
		else
		{
			String text = ArrayUtils.stringify(event.args, " ");

			GoogleTtsApi api = new GoogleTtsApi();
			api.setLang("en");
			api.getTts(text);

			String path = FileUtils.randomTmpFile("mp3");

			api.download(path);

			event.event.getChannel().sendFile(new File(path)).queue();
		}
	}

	@Override
	public void on_register() 
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Sends a mp3 file of the text specified";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%stts [text]' to send a mp3 file of the text specified", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String text = event.getOption("text").getAsString();

		event.reply("Please wait...").queue();

		GoogleTtsApi api = new GoogleTtsApi();
		api.setLang("en");
		api.getTts(text);

		String path = FileUtils.randomTmpFile("mp3");

		api.download(path);

		event.getHook().editOriginal(new File(path)).queue();
		event.getHook().editOriginal("").queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("tts", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("text", "Text to say", SlashCommandParameter.STRING, true)
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
