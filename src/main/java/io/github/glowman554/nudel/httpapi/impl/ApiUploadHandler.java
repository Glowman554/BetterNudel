package io.github.glowman554.nudel.httpapi.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import io.github.glowman554.nudel.utils.TokenUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class ApiUploadHandler implements HttpApiHandler
{
	String upload_path = Main.http_host_path + "/files/";

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		String token = query.get("token");
		TokenUtils.checkToken(token);

		Json _json = Json.json();

		if (query.get("delete") == null)
		{
			JsonNode root = JsonNode.array();

			Files.walk(new File(this.upload_path).toPath()).forEach(path -> {
				if (Files.isRegularFile(path) && path.toString().endsWith("!!hidden!!.json"))
				{
					try
					{
						String content = FileUtils.readFile(path.toString());

						JsonNode node = _json.parse(content);

						root.add(node);
					}
					catch (IOException|JsonSyntaxException e)
					{
						e.printStackTrace();
					}
				}
			});

			return _json.serialize(root);
		}
		else
		{
			String file = query.get("id");

			if (file == null)
			{
				return "missing id";
			}

			String path = this.upload_path + file;

			if (!Files.exists(new File(path).toPath()))
			{
				return "file not found";
			}

			new File(path).delete();
			new File(path + "!!hidden!!.json").delete();

			return "deleted " + file;
		}
	}
	
}
