package io.github.glowman554.nudel.discord.commands;

import java.util.HashMap;

import io.github.glowman554.nudel.discord.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class CommandManager
{
	public HashMap<String, Command> commands;
	public HashMap<String, SlashCommand> slashCommands;	
	public final String prefix;
	public PermissionManager permissionManager;

	public CommandManager(String prefix)
	{
		this.prefix = prefix;
		commands = new HashMap<String, Command>();
		slashCommands = new HashMap<String, SlashCommand>();
		permissionManager = new PermissionManager("perms.json");
	}

	public void onCommand(CommandEvent event) throws Exception
	{
		if (!event.command.startsWith(prefix))
		{
			return;
		}
		
		if (event.command.equals(prefix + "help"))
		{
			switch (event.args.length)
			{
				case 0:
					{
						EmbedBuilder builder = new EmbedBuilder();
						builder.setTitle("Help");

						commands.forEach((key, value) -> {
							//System.out.println(key);
							builder.addField(key, value.get_short_help(), false);
						});

						event.event.getChannel().sendMessage(builder.build()).queue();
					}
					break;
				
				case 1:
					{
						if (!event.args[0].startsWith(Discord.discord.commandManager.prefix))
						{
							event.args[0] = Discord.discord.commandManager.prefix + event.args[0];
						}

						Command command = commands.get(event.args[0]);
						if (command != null)
						{
							EmbedBuilder builder = new EmbedBuilder();
							builder.setTitle(event.args[0]);
							builder.setDescription(command.get_long_help());
							event.event.getChannel().sendMessage(builder.build()).queue();
						}
						else
						{
							event.event.getChannel().sendMessage("Command not found.").queue();
						}
					}
					break;

				default:
					event.event.getChannel().sendMessage("Too many arguments.").queue();
					break;
			}
		}
		else
		{
			Command command = commands.get(event.command);

			if (command != null)
			{
				String permission = command.get_permission();

				if (permission != null)
				{
					if (!permissionManager.hasPermission(event.event.getAuthor().getId(), permission))
					{
						event.permFail();
						return;
					}
				}

				command.execute(event);
			}
			else
			{
				event.commandFail("Command not found.");
			}
		}
	}

	public void onSlashCommand(SlashCommandEvent event) throws Exception
	{
		SlashCommand command = slashCommands.get(event.getName());

		if (command != null)
		{
			String permission = command.get_permission();

			if (permission != null)
			{
				if (!permissionManager.hasPermission(event.getUser().getId(), permission))
				{
					event.reply("You do not have permission to use this command.").queue();
					return;
				}
			}

			command.execute(event);
		}
		else
		{
			event.reply("Command not found.").queue();
		}
	}

	public void addCommand(String what, Command command)
	{
		what = prefix + what;

		command.on_register();

		commands.put(what, command);
		System.out.printf("[%s] Command register complete\n", what);
	}

	public void addSlashCommand(String what, SlashCommand command)
	{
		command.on_slash_register();

		slashCommands.put(what, command);
		System.out.printf("[%s] Slash command register complete\n", what);
	}
}