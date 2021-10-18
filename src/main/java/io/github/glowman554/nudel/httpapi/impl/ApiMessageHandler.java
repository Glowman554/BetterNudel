package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.dv8tion.jda.api.entities.TextChannel;

public class ApiMessageHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String body = query.get("body");
		if (body == null)
		{
			return "Missing body";
		}

		String token = query.get("token");
		if (token == null)
		{
			return "Missing token";
		}

		String channel = query.get("channel");
		if (channel == null)
		{
			return "Missing channel";
		}

		if (!token.equals(System.getenv("TOKEN")))
		{
			return "Invalid token";
		}

		TextChannel c = (TextChannel) Discord.discord.jda.getGuildChannelById(channel);

		if (c == null)
		{
			return "Invalid channel";
		}

		c.sendMessage(body).queue();
		
		return "Message sent";
	}
	
}
