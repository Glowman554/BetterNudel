package io.github.glowman554.nudel.httpapi.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class ApiCommandsHandler implements HttpApiHandler
{
	public String execute(java.util.Map<String,String> query) throws Exception
	{
		Json _json = Json.json();
		JsonNode root = JsonNode.object();

		JsonNode commands = JsonNode.array();

		for (String command : Discord.discord.commandManager.commands.keySet())
		{
			Command cmd = Discord.discord.commandManager.commands.get(command);
			JsonNode command_node = JsonNode.object();

			command_node.set("name", command);

			JsonNode help = JsonNode.object();
			help.set("short", cmd.get_short_help());
			help.set("long", cmd.get_long_help());

			command_node.set("help", help);
			command_node.set("permission", cmd.get_permission());

			commands.add(command_node);
		}

		root.set("commands", commands);

		JsonNode slash_commands = JsonNode.array();

		for (String command : Discord.discord.commandManager.slashCommands.keySet())
		{
			SlashCommand cmd = Discord.discord.commandManager.slashCommands.get(command);
			JsonNode command_node = JsonNode.object();

			command_node.set("name", command);
			command_node.set("permission", cmd.get_permission());

			slash_commands.add(command_node);
		}

		root.set("slash_commands", slash_commands);

		return _json.serialize(root);
	}
}
