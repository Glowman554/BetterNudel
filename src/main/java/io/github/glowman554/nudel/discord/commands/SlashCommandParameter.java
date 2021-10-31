package io.github.glowman554.nudel.discord.commands;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class SlashCommandParameter
{
	public String name = null;
	public String description = null;
	public int type;
	public boolean required;

	public String[] choices = null;

	public static int
	STRING = 3,	
	INTEGER = 4,
	BOOLEAN = 5,
	USER = 6,
	CHANNEL = 7,
	ROLE = 8,
	MENTIONABLE = 9,
	NUMBER = 10;

	public SlashCommandParameter(String name, String description, int type, boolean required, String[] choices)
	{
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
		this.choices = choices;
	}

	public SlashCommandParameter(String name, String description, int type, boolean required)
	{
		this.name = name;
		this.description = description;
		this.type = type;
		this.required = required;
	}

	public JsonNode json()
	{
		JsonNode root = JsonNode.object();

		root.set("name", JsonNode.string(name));
		root.set("description", JsonNode.string(description));
		root.set("type", JsonNode.number(type));
		root.set("required", JsonNode.bool(required));

		if (choices != null)
		{
			JsonNode choicesNode = JsonNode.array();
			for (String choice : choices)
			{
				JsonNode _choice = JsonNode.object();
				_choice.set("name", JsonNode.string(choice));
				_choice.set("value", JsonNode.string(choice.replace(" ", "_").toLowerCase()));
				choicesNode.add(_choice);
			}
			root.set("choices", choicesNode);
		}

		return root;
	}

	@Override
	public String toString()
	{
		Json json = Json.json();

		return json.serialize(json());
	}
}
