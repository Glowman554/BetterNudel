package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.exec.ExecutionEngine;
import io.github.glowman554.nudel.utils.ArrayUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class ExecCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("Please specify a command to execute.");
		}
		else
		{
			String command = ArrayUtils.stringify(event.args, " ");

			ExecutionEngine engine = new ExecutionEngine(!Main.parser.is_option("--allow-unsafe-exec"));

			event.commandSuccess(engine.execute(command));
		}
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Execute a shell command.";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%sexec [command]' to execute a shell command.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return "exec";
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String command = event.getOption("command").getAsString();

		event.reply("Executing command...").queue();

		ExecutionEngine engine = new ExecutionEngine(!Main.parser.is_option("--allow-unsafe-exec"));

		event.getHook().editOriginal(engine.execute(command)).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("exec", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("command", "Command to execute", SlashCommandParameter.STRING, true)
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
