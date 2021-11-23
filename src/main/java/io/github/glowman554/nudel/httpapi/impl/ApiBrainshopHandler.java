package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.api.ChatBotApi;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class ApiBrainshopHandler implements HttpApiHandler
{
	private ChatBotApi api = new ChatBotApi();

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String message = query.get("msg");
		
		if (message == null)
		{
			return "Missing msg";
		}
		
		String uid = query.get("uid");
		
		if (uid == null)
		{
			return "Missing uid";
		}
		
		String response = api.response(message, uid);
		
		Json json = Json.json();
		
		JsonNode root = JsonNode.object();
		root.set("cnt", response);
		
		return json.serialize(root);
	}

}
