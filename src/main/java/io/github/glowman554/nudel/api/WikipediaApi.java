package io.github.glowman554.nudel.api;

import java.io.IOException;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class WikipediaApi extends BaseApi
{
	public String searchWiki(String query) throws IOException, JsonSyntaxException
	{
		query = query.replaceAll(" ", "%20");
		
		String res = request("https://en.wikipedia.org/api/rest_v1/page/summary/" + query);

		Json json = Json.json();
		JsonNode root = json.parse(res);

		return root.get("extract").asString();
	}
}
