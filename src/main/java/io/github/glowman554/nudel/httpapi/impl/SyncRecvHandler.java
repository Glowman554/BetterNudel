package io.github.glowman554.nudel.httpapi.impl;

import java.io.IOException;
import java.util.Map;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import io.github.glowman554.nudel.utils.TokenUtils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class SyncRecvHandler extends ListenerAdapter implements HttpApiHandler
{
	public boolean enabled = false;

	public SyncRecvHandler()
	{
		Discord.discord.jda.addEventListener(this);
		System.out.println("[host sync] Listener added");
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		super.onMessageReceived(event);

		if (!enabled)
		{
			return;
		}

		try
		{
			if (event.getAuthor().isBot() && event.getAuthor().getId().equals(Discord.discord.jda.getSelfUser().getId()))
			{
				if (event.getMessage().getContentRaw().startsWith("!!host-sync!!"))
				{
					String extracted_json = event.getMessage().getContentRaw().replace("!!host-sync!!", "");
					System.out.println("[host sync] Received: " + extracted_json);

					Json _json = Json.json();
					JsonNode root_node = _json.parse(extracted_json);

					String file_path = Main.http_host_path + "/files/" + root_node.get("file_id").asString();
					event.getMessage().getAttachments().get(0).downloadToFile(file_path);

					FileUtils.writeFile(file_path + "!!hidden!!.json", extracted_json);

					event.getMessage().delete().queue();
				}
			}
		}
		catch (JsonSyntaxException | IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);
		
		String enable = query.get("enable");
		if (enable == null)
		{
			return "enable parameter is missing";
		}

		if (enable.equals("true"))
		{
			enabled = true;
			return "enabled";
		}
		else if (enable.equals("false"))
		{
			enabled = false;
			return "disabled";
		}
		else
		{
			return "enable parameter must be true or false";
		}
	}
	
}
