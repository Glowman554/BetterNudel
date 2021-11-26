package io.github.glowman554.nudel.discord.commands.impl;

import java.io.File;
import java.util.HashMap;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class UploadCommand implements Command
{
	String upload_path = Main.http_host_path + "/files/";
	HashMap<String, Long> rate_limit = new HashMap<String, Long>();

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 0 || event.event.getMessage().getAttachments().size() == 0)
		{
			event.commandFail("Invalid arguments or missing file");
		}
		else
		{
			if (rate_limit.containsKey(event.event.getAuthor().getId()))
			{
				long last_upload = rate_limit.get(event.event.getAuthor().getId());
				int rate_limit_time = 1000 * 60;
				if (System.currentTimeMillis() - last_upload < rate_limit_time)
				{
					event.commandFail("Rate limit exceeded please wait " + ((rate_limit_time - (System.currentTimeMillis() - last_upload)) / 1000) + "s");
					return;
				}
				else
				{
					rate_limit.put(event.event.getAuthor().getId(), System.currentTimeMillis());
				}
			}
			else
			{
				rate_limit.put(event.event.getAuthor().getId(), System.currentTimeMillis());
			}

			String ret = String.format("Uploaded %s files to:\n", event.event.getMessage().getAttachments().size());

			Json _json = Json.json();

			for (int i = 0; i < event.event.getMessage().getAttachments().size(); i++)
			{
				String file_path = FileUtils.randomFileId(FileUtils.getFileExtension(event.event.getMessage().getAttachments().get(i).getFileName()));

				event.event.getMessage().getAttachments().get(i).downloadToFile(upload_path + file_path);

				ret += Main.http_host_url + "files/" + file_path + "\n";

				JsonNode root = JsonNode.object();
				root.set("file_id", file_path);
				root.set("original_name", event.event.getMessage().getAttachments().get(i).getFileName());
				root.set("upload_time", System.currentTimeMillis());
				root.set("download_url", Main.http_host_url + "files/" + file_path);

				JsonNode uploader_info = JsonNode.object();
				uploader_info.set("id", event.event.getAuthor().getId());
				uploader_info.set("name", event.event.getAuthor().getName());
				uploader_info.set("discriminator", event.event.getAuthor().getDiscriminator());
				uploader_info.set("avatar_url", event.event.getAuthor().getAvatarUrl());

				root.set("uploader", uploader_info);

				FileUtils.writeFile(upload_path + file_path + "!!hidden!!.json", _json.serialize(root));
			}

			event.commandSuccess(ret);
		}
	}

	@Override
	public void on_register()
	{
		if (!new File(upload_path).exists())
		{
			System.out.println("Creating directory: " + upload_path);
			new File(upload_path).mkdir();
		}
	}

	@Override
	public String get_short_help()
	{
		return null;
	}

	@Override
	public String get_long_help()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_permission()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}
