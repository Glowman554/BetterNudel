package io.github.glowman554.nudel.plugin;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;

public class JSPluginCommand implements Command
{
	public String short_help;
	public String long_help;
	public String permission;

	public ScriptObjectMirror commandExecutor;

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		this.commandExecutor.call(this.commandExecutor, event);
	}

	@Override
	public void on_register()
	{
	}

	@Override
	public String get_short_help()
	{
		return short_help;
	}

	@Override
	public String get_long_help()
	{
		return long_help;
	}

	@Override
	public String get_permission()
	{
		return permission;
	}
}