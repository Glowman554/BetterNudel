package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class LoadPluginCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			if (event.event.getMessage().getAttachments().size() != 1)
			{
				event.commandFail("You must provide a file or url to load.");
				return;
			}
			else
			{
				String plugin_path = Main.pluginsLoader.plugin_dir + "/" + event.event.getMessage().getAttachments().get(0).getFileName();
				event.event.getMessage().getAttachments().get(0).downloadToFile(plugin_path).join();
				Main.pluginsLoader.load_from_url_or_path(plugin_path);
			}
		}
		else if (event.args.length == 1)
		{
			Main.pluginsLoader.load_from_url_or_path(event.args[0]);
		}
		else
		{
			event.commandFail("Invalid arguments.");
		}
	}

	@Override
	public void on_register()
	{
	}

	@Override
	public String get_short_help()
	{
		return "Loads a plugin from a url.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sload-plugin' to load a plugin from a url or file!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return "plugin";
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String url = event.getOption("url").getAsString();

		event.reply("Loading plugin from url: " + url + "...").queue();

		Main.pluginsLoader.load_from_url_or_path(url);

		event.getHook().editOriginal("Plugin loaded.").queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("load-plugin", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("url", "Url to load plugin from.", SlashCommandParameter.STRING, true)
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
