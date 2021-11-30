package io.github.glowman554.nudel.plugin;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import io.github.glowman554.nudel.discord.commands.SlashCommand;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class JSPluginSlashCommand implements SlashCommand
{
	public String permission;

	public ScriptObjectMirror commandExecutor;
	public ScriptObjectMirror commandRegistrar;

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		this.commandExecutor.call(this.commandExecutor, event);
	}

	@Override
	public void on_slash_register()
	{
		this.commandRegistrar.call(this.commandRegistrar, this);
	}

	@Override
	public String get_permission()
	{
		return this.permission;
	}
	
}
