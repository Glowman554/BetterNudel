package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class ApiEndpointsHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		Json _json = Json.json();
		JsonNode root = JsonNode.array();

		for (String endpoint : Main.http_api.handlers.keySet())
		{
			root.add(endpoint);
		}

		return _json.serialize(root);
	}
	
}
