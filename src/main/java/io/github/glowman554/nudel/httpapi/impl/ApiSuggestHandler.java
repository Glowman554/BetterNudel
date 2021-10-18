package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class ApiSuggestHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String suggestion = query.get("suggestion");
		if (suggestion == null)
		{
			return "Missing suggestion";
		}

		String ip = query.get("ip");
		if (ip == null)
		{
			return "Missing ip";
		}

		TextChannel c = (TextChannel) Discord.discord.jda.getGuildChannelById(System.getenv("NOTIFY_CHANNEL"));

		EmbedBuilder eb = new EmbedBuilder();
		eb.setThumbnail("https://thumbs.dreamstime.com/z/suggestions-list-yellow-office-note-18266076.jpg");
		eb.setTitle("Suggestion");
		eb.setDescription(suggestion);
		eb.addField("IP", ip, false);
		c.sendMessage(eb.build()).queue();

		return "Success";
	}
	
}
