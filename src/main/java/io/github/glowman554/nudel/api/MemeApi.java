package io.github.glowman554.nudel.api;

import java.io.IOException;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class MemeApi extends BaseApi
{
	public String _url;

	public String getMeme() throws IOException, JsonSyntaxException
	{
		String res = request("https://meme-api.herokuapp.com/gimme");

		Json json = Json.json();
		JsonNode root = json.parse(res);

		_url = root.get("url").asString();

		return _url;
	}

	public void download(String path) throws IOException
	{
		download(path, _url);
	}
}
