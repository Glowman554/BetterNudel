package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;

public class ApiStatusHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		if (token == null)
		{
			return "Missing token";
		}
		
		if (!token.equals(System.getenv("TOKEN")))
		{
			return "Invalid token";
		}

		String status = query.get("status");

		if (status == null)
		{
			return "The query parameter status is missing!";
		}
		else
		{
			Discord.discord.setRP(status);

			return "OK";
		}
	}
	
}
