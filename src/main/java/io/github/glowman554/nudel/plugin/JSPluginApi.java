package io.github.glowman554.nudel.plugin;

import java.io.IOException;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.httpapi.HttpApiBaseHandler;

public class JSPluginApi
{
	public static void register_command(String name, String short_help, String long_help, String permission, ScriptObjectMirror commandExecutor)
	{
		JSPluginCommand command = new JSPluginCommand();
		command.short_help = short_help;
		command.long_help = long_help;
		command.permission = permission;
		command.commandExecutor = commandExecutor;

		Discord.discord.commandManager.addCommand(name, command);
	}

	public static void register_slash_command(String name, String permission, ScriptObjectMirror commandExecutor, ScriptObjectMirror commandRegistrar)
	{
		JSPluginSlashCommand command = new JSPluginSlashCommand();
		command.permission = permission;
		command.commandExecutor = commandExecutor;
		command.commandRegistrar = commandRegistrar;

		Discord.discord.commandManager.addSlashCommand(name, command);
	}

	public static SlashCommandRegister create_slash_registerer(String name, String description, int type, SlashCommandParameter[] options)
	{
		return new SlashCommandRegister(name, description, type, options);
	}

	public static SlashCommandParameter create_slash_parameter(String name, String description, int type, boolean required, String[] choices)
	{
		SlashCommandParameter param;

		if (choices != null)
		{
			param = new SlashCommandParameter(name, description, type, required, choices);
		}
		else
		{
			param = new SlashCommandParameter(name, description, type, required);
		}

		return param;
	}

	public static void do_slash_command_register(SlashCommandRegister register) throws IOException
	{
		register.doRegister(Discord.discord.token, Discord.discord.application_id);
	}

	public static void register_http_handler(String path, ScriptObjectMirror handler)
	{
		JSHttpHandler httpHandler = new JSHttpHandler();
		httpHandler.handler = handler;

		new HttpApiBaseHandler(httpHandler, Main.http_api, path);
	}

	public static String request(String url) throws IOException
	{
		return new BaseApi().request(url);
	}

	public static void download(String url, String path) throws IOException
	{
		new BaseApi().download(path, url);
	}

	public static void sleep(int ms) throws InterruptedException
	{
		Thread.sleep(ms);
	}

	public static void set_timeout(ScriptObjectMirror executor, int ms)
	{
		new Thread("js-timeout") {
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(ms);
					executor.call(executor);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
