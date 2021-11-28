package io.github.glowman554.nudel.httpapi.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import io.github.glowman554.nudel.utils.TokenUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class SyncSendHandler implements HttpApiHandler
{
	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);
		
		String channel = query.get("channel");
		if (channel == null)
		{
			return "channel is required";
		}

		new Thread("Host sync sender") {
			public void run()
			{
				String upload_path = Main.http_host_path + "/files/";
				Json _json = Json.json();

				TextChannel _channel = (TextChannel) Discord.discord.jda.getGuildChannelById(channel);

				try
				{
					Files.walk(new File(upload_path).toPath()).forEach(path -> {
						if (Files.isRegularFile(path) && path.toString().endsWith("!!hidden!!.json"))
						{
							try
							{
								String content = FileUtils.readFile(path.toString());

								JsonNode node = _json.parse(content);

								String file_path = upload_path + node.get("file_id").asString();
								System.out.println("[host sync] Sending file " + file_path + " to channel " + channel);

								_channel.sendMessage("!!host-sync!!" + node.toString()).addFile(new File(file_path)).queue();
							}
							catch (IOException|JsonSyntaxException e)
							{
								e.printStackTrace();
							}
						}
					});
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();

		return "Started sending to channel " + channel;
	}
	
}
