package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class RoleCommand implements Command, SlashCommand
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		MessageChannel channel = event.event.getChannel();		
		Message message = channel.retrieveMessageById(event.event.getMessageId()).complete();

		if(event.args.length < 1)
		{
			event.commandFail("Expected at least 1 arguments");
		}
		else
		{
			switch (event.args[0])
			{
				case "add":
					{
						if (event.args.length != 3 ||  message.getMentionedUsers().size() != 1)
						{
							event.commandFail("Expected 3 arguments");
						}
						else
						{
							String user = message.getMentionedUsers().get(0).getId();
							String role = event.args[2];

							if (user == null)
							{
								event.commandFail("Please mention a user");
							}
							else
							{
								Discord.discord.commandManager.permissionManager.addPermission(user, role);

								event.commandSuccess("Added <@!" + user + "> to " + role);
							}
						}
					}
					break;
				
				case "remove":
					{
						if (event.args.length != 3 ||  message.getMentionedUsers().size() != 1)
						{
							event.commandFail("Expected 3 arguments");
						}
						else
						{
							String user = message.getMentionedUsers().get(0).getId();
							String role = event.args[2];

							if (user == null)
							{
								event.commandFail("Please mention a user");
							}
							else
							{
								Discord.discord.commandManager.permissionManager.removePermission(user, role);

								event.commandSuccess("Removed <@!" + user + "> from " + role);
							}
						}
					}
					break;
				
				case "list":
					{
						if (event.args.length != 2 ||  message.getMentionedUsers().size() != 1)
						{
							if (event.args.length == 1)
							{
								String user = message.getAuthor().getId();

								StringBuilder sb = new StringBuilder();
								sb.append("Permissions for <@!" + user + ">: ");
								
								for (String role : Discord.discord.commandManager.permissionManager.getPermissions(user))
								{
									sb.append(role + ", ");
								}

								sb.deleteCharAt(sb.length() - 2);
								
								event.commandSuccess(sb.toString());
							}
							else
							{
								event.commandFail("Expected 2 arguments");
							}
						}
						else
						{
							String user = message.getMentionedUsers().get(0).getId();

							if (user == null)
							{
								event.commandFail("Please mention a user");
							}
							else
							{
								StringBuilder sb = new StringBuilder();
								sb.append("Permissions for <@!" + user + ">: ");
								
								for (String role : Discord.discord.commandManager.permissionManager.getPermissions(user))
								{
									sb.append(role + ", ");
								}

								sb.deleteCharAt(sb.length() - 2);
								
								event.commandSuccess(sb.toString());
							}
						}
					}
					break;
				
				default:
					event.commandFail("Unknown command");
					break;
			}
		}
		
	}

	@Override
	public void on_register()
	{
		
	}

	@Override
	public String get_short_help()
	{
		return "Manage permissions!";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%srole [add,remove,list] [user?] [role?]' to manage permissions.", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return "role";
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		String function = event.getOption("sub_command").getAsString();

		switch (function)
		{
			case "add":
				{
					if (event.getOption("user") == null || event.getOption("role") == null)
					{
						event.reply("Please specify a user and a role").queue();;
					}
					else
					{
						String user = event.getOption("user").getAsString();
						String role = event.getOption("role").getAsString();

						Discord.discord.commandManager.permissionManager.addPermission(user, role);

						event.reply("Added <@!" + user + "> to " + role).queue();
					}
				}
				break;
			
			case "remove":
				{
					if (event.getOption("user") == null || event.getOption("role") == null)
					{
						event.reply("Please specify a user and a role").queue();;
					}
					else
					{
						String user = event.getOption("user").getAsString();
						String role = event.getOption("role").getAsString();

						Discord.discord.commandManager.permissionManager.removePermission(user, role);

						event.reply("Removed <@!" + user + "> from " + role).queue();
					}
				}
				break;
			
			case "list":
				{
					String user = event.getUser().getId();

					if (event.getOption("user") != null)
					{
						user = event.getOption("user").getAsString();
					}
					

					StringBuilder sb = new StringBuilder();
					sb.append("Permissions for <@!" + user + ">: ");
						
					for (String role : Discord.discord.commandManager.permissionManager.getPermissions(user))
					{
						sb.append(role + ", ");
					}

					sb.deleteCharAt(sb.length() - 2);
						
					event.reply(sb.toString()).queue();
				}
				break;
		}
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("role", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("sub_command", "Function to performe", SlashCommandParameter.STRING, true, new String[] { "add", "remove", "list" }),
			new SlashCommandParameter("user", "User to fetch pronouns from", SlashCommandParameter.USER, false),
			new SlashCommandParameter("role", "Role to fetch pronouns from", SlashCommandParameter.STRING, false)
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
