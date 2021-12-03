package io.github.glowman554.nudel.httpapi.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class ApiCollectV2Handler implements HttpApiHandler
{
	String file;

	Json _json = Json.json();
	JsonNode root;

	public ApiCollectV2Handler(String file) throws JsonSyntaxException, IOException
	{
		this.file = file;

		if (new File(file).exists())
		{
			root = _json.parse(FileUtils.readFile(file));
		}
		else
		{
			root = _json.parse("{\"known_users\": []}");
			FileUtils.writeFile(file, _json.serialize(root));
		}
	}

	private JsonNode createUser(String ip, String user_agent)
	{
		JsonNode user = JsonNode.object();
		user.set("ip", ip);
		user.set("user_agent", JsonNode.array());
		user.get("user_agent").add(user_agent);
		user.set("last_seen", System.currentTimeMillis());

		return user;
	}

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String user_agent = query.get("user_agent");
		if (user_agent == null)
		{
			return "Missing user_agent";
		}

		String ip = query.get("ip");
		if (ip == null)
		{
			return "Missing ip";
		}

		for (JsonNode user : root.get("known_users"))
		{
			if (user.get("ip").asString().equals(ip))
			{
				boolean found_user_agent = false;
				for (JsonNode ua : user.get("user_agent"))
				{
					if (ua.asString().equals(user_agent))
					{
						found_user_agent = true;
						break;
					}
				}

				if (!found_user_agent)
				{
					user.get("user_agent").add(user_agent);
				}

				user.set("last_seen", System.currentTimeMillis());
				
				FileUtils.writeFile(file, _json.serialize(root));

				return _json.serialize(user);
			}
		}

		JsonNode user = createUser(ip, user_agent);
		root.get("known_users").add(user);

		FileUtils.writeFile(file, _json.serialize(root));

		return _json.serialize(user);
	}
}
