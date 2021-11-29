package io.github.glowman554.nudel.plugin;

import java.io.IOException;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.discord.Discord;
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
		new BaseApi().download(url, path);
	}
}
