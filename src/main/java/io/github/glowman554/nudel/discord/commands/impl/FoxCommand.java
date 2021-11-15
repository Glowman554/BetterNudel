package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;
import java.io.IOException;

import io.github.glowman554.nudel.api.FoxApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class FoxCommand implements Command, SlashCommand
{
	
	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0)
		{
			event.commandFail("Command takes no arguments");
		}
		else
		{
			FoxApi api = new FoxApi();
			api.getFox();

			String file = FileUtils.randomTmpFile(FileUtils.getFileExtension(api._url));
			api.download(file);

			event.event.getChannel().sendFile(new File(file)).queue();
		}
		
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "See a cute fox!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sfox' to see a cute cat!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		event.reply("Please wait...").queue();
	
		FoxApi api = new FoxApi();
		api.getFox();

		String file = FileUtils.randomTmpFile(FileUtils.getFileExtension(api._url));
		api.download(file);

		event.getHook().editOriginal(new File(file)).queue();
		event.getHook().editOriginal("").queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("fox", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {});

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
