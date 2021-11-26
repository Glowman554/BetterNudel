package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.TokenUtils;
import net.dv8tion.jda.api.entities.TextChannel;

public class ApiMessageHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);
		
		String body = query.get("body");
		if (body == null)
		{
			return "Missing body";
		}

		String channel = query.get("channel");
		if (channel == null)
		{
			return "Missing channel";
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
