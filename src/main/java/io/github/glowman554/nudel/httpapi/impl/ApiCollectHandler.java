package io.github.glowman554.nudel.httpapi.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class ApiCollectHandler implements HttpApiHandler
{
	String file;

	Json _json = Json.json();
	JsonNode root;

	public ApiCollectHandler(String file) throws JsonSyntaxException, IOException
	{
		this.file = file;

		if (new File(file).exists())
		{
			root = _json.parse(FileUtils.readFile(file));
		}
		else
		{
			root = _json.parse("{\"num_visits\": 0, \"last_visit\": 0, \"known_agents\": []}");
			FileUtils.writeFile(file, _json.serialize(root));
		}
	}

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String user_agent = query.get("user_agent");
		if (user_agent == null)
		{
			return "Missing user_agent";
		}

		root.set("num_visits", root.get("num_visits").asInt() + 1);
		root.set("last_visit", System.currentTimeMillis());

		JsonNode known_agents = root.get("known_agents");

		boolean found = false;

		for (String agent : known_agents.asStringArray())
		{
			if (agent.equals(user_agent))
			{
				found = true;
				break;
			}
		}

		if (!found)
		{
			known_agents.add(user_agent);
		}

		FileUtils.writeFile(file, _json.serialize(root));

		return _json.serialize(root);
	}
	
}
