package io.github.glowman554.nudel.discord.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public interface SlashCommand
{
	void execute(SlashCommandEvent event) throws Exception;

	void on_slash_register();	

	String get_permission();
}
