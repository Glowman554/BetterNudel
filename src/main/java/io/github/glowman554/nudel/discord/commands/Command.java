package io.github.glowman554.nudel.discord.commands;

public interface Command
{
	void execute(CommandEvent event) throws Exception;

	void on_register();	

	String get_short_help();
	String get_long_help();
	String get_permission();
}
