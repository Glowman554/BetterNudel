package io.github.glowman554.nudel.discord.commands;

import java.io.IOException;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SlashCommandRegister
{
	public String name = null;
	public String description = null;
	public int type = 0;

	public SlashCommandParameter[] options = null;

	public static int
	CHAT_INPUT = 1,
	USER = 2,
	MESSAGE = 3;

	public SlashCommandRegister(String name, String description, int type, SlashCommandParameter[] options)
	{
		this.name = name;
		this.description = description;
		this.type = type;
		this.options = options;
	}


	public void doRegister(String token, String app_id) throws IOException
	{
		Json _json = Json.json();
		String root = _json.serialize(json());

		System.out.println("[Discord slash] trying to register command: " + name);

		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(MediaType.parse("application/json"), root);
		Request request = new Request.Builder().url("https://discord.com/api/v8/applications/" + app_id + "/commands").post(body).addHeader("Authorization", "Bot " + token).build();

		Call call = client.newCall(request);
		Response response = call.execute();

		String res_body = response.body().string();

		System.out.println("[Discord slash] response: " + res_body);

		try
		{
			JsonNode response_root = _json.parse(res_body);

			if (response_root.get("retry_after") != null)
			{
				System.out.println("[Discord slash] retry after: " + response_root.get("retry_after").asFloat());
				Thread.sleep((int) (response_root.get("retry_after").asFloat() * 1000));

				doRegister(token, app_id);
			}
		}
		catch (JsonSyntaxException|InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public JsonNode json()
	{
		JsonNode root = JsonNode.object();

		root.set("name", JsonNode.string(name));
		root.set("description", JsonNode.string(description));
		root.set("type", JsonNode.number(type));

		if (options!= null)
		{
			JsonNode optionsNode = JsonNode.array();
			for (SlashCommandParameter option : options)
			{
				optionsNode.add(option.json());
			}
			root.set("options", optionsNode);
		}

		return root;
	}

	@Override
	public String toString()
	{
		Json json = Json.json();

		return json.serialize(json());
	}
}