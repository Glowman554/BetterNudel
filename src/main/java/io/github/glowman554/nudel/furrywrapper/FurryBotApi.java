package io.github.glowman554.nudel.furrywrapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class FurryBotApi
{
	public String[] _methods = {
		"animals/birb",
		"animals/blep",
		"furry/boop",
		"furry/cuddle",
		"furry/flop",
		"furry/fursuit",
		"furry/hold",
		"furry/howl",
		"furry/hug",
		"furry/kiss",
		"furry/lick",
		"furry/propose",
		"furry/bulge",
		"furry/yiff/gay",
		"furry/yiff/straight",
		"furry/yiff/lesbian",
		"furry/yiff/gynomorph"
	};

	private String request(String endpoint) throws IOException
	{
		URL url = new URL(String.format("https://yiff.rest/v2/%s?limit=1", endpoint));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		con.setRequestProperty("Accept", "application/json");
		
		String response = "";

		for(byte b : con.getInputStream().readAllBytes())
		{
			response += (char) b;
		}

		con.getInputStream().close();
		con.disconnect();

		return response;
	}

	public FurryResult random_image(String endpoint) throws IOException, JsonSyntaxException
	{
		String response = request(endpoint);

		Json json = Json.json();
		JsonNode root = json.parse(response);
		String url = root.get("images").get(0).get("url").asString();

		return new FurryResult(url);
	}

	public FurryResult animal_birb() throws IOException, JsonSyntaxException
	{
		return random_image("animals/birb");
	}

	public FurryResult animal_blep() throws IOException, JsonSyntaxException
	{
		return random_image("animals/blep");
	}

	public FurryResult furry_boop() throws IOException, JsonSyntaxException
	{
		return random_image("furry/boop");
	}

	public FurryResult furry_cuddle() throws IOException, JsonSyntaxException
	{
		return random_image("furry/cuddle");
	}

	public FurryResult furry_flop() throws IOException, JsonSyntaxException
	{
		return random_image("furry/flop");
	}

	public FurryResult furry_fursuit() throws IOException, JsonSyntaxException
	{
		return random_image("furry/fursuit");
	}

	public FurryResult furry_hold() throws IOException, JsonSyntaxException
	{
		return random_image("furry/hold");
	}

	public FurryResult furry_howl() throws IOException, JsonSyntaxException
	{
		return random_image("furry/howl");
	}

	public FurryResult furry_hug() throws IOException, JsonSyntaxException
	{
		return random_image("furry/hug");
	}

	public FurryResult furry_kiss() throws IOException, JsonSyntaxException
	{
		return random_image("furry/kiss");
	}

	public FurryResult furry_lick() throws IOException, JsonSyntaxException
	{
		return random_image("furry/lick");
	}

	public FurryResult furry_propose() throws IOException, JsonSyntaxException
	{
		return random_image("furry/propose");
	}

	public FurryResult furry_bulge() throws IOException, JsonSyntaxException
	{
		return random_image("furry/bulge");
	}

	public FurryResult furry_yiff_gay() throws IOException, JsonSyntaxException
	{
		return random_image("furry/yiff/gay");
	}

	public FurryResult furry_yiff_straight() throws IOException, JsonSyntaxException
	{
		return random_image("furry/yiff/straight");
	}

	public FurryResult furry_yiff_lesbian() throws IOException, JsonSyntaxException
	{
		return random_image("furry/yiff/lesbian");
	}

	public FurryResult furry_yiff_gynomorph() throws IOException, JsonSyntaxException
	{
		return random_image("furry/yiff/gynomorph");
	}
}
