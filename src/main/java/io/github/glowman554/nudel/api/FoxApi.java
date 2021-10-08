package io.github.glowman554.nudel.api;

import java.io.IOException;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class FoxApi extends BaseApi
{
	public String _url;

	public String getFox() throws IOException, JsonSyntaxException
	{
		String res = request("https://randomfox.ca/floof/?ref=apilist.fun");

		Json json = Json.json();
		JsonNode root = json.parse(res);

		_url = root.get("image").asString();

		return _url;
	}

	public void download(String path) throws IOException
	{
		download(path, _url);
	}
}
