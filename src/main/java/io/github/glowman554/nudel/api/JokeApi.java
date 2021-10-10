package io.github.glowman554.nudel.api;

import java.io.IOException;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class JokeApi extends BaseApi
{
	public String getJoke() throws IOException, JsonSyntaxException
	{
		String res = request("http://api.icndb.com/jokes/random");
		Json json = Json.json();
		JsonNode root = json.parse(res);

		return root.get("value").get("joke").asString();
	}
}
