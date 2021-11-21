package io.github.glowman554.nudel.api;

import java.io.IOException;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class ChatBotApi extends BaseApi
{
	// https://brainshop.ai/
	public static String key = null;
	public static String bid = null;

	public ChatBotApi()
	{
		if (System.getenv("BRAIN_SHOP_KEY") != null && System.getenv("BRAIN_SHOP_BID") != null)
		{
			key = System.getenv("BRAIN_SHOP_KEY");
			bid = System.getenv("BRAIN_SHOP_BID");

			System.out.printf("[ChatBotApi] Loaded Brain Shop key '%s' and bid '%s' from environment variables.\n", key, bid);
		}
		else
		{
			try
			{
				String config = FileUtils.readFile(Main.config_file);

				Json config_json = Json.json();
				JsonNode config_root = config_json.parse(config);

				key = config_root.get("brain_shop_key").asString();
				bid = config_root.get("brain_shop_bid").asString();

				System.out.printf("[ChatBotApi] Loaded Brain Shop key '%s' and bid '%s' from config file.\n", key, bid);
			}
			catch (IOException | JsonSyntaxException e)
			{
				e.printStackTrace();
			}
		}

		if (key == null || bid == null)
		{
			System.out.println("[ChatBotApi] Could not load Brain Shop key and bid. Please set them in the config file or in the environment variables.");
		}
	}

	public String response(String message, String id) throws IOException, JsonSyntaxException
	{
		message = message.replace("#", "%23");
		message = message.replace("&", "%26");
		message = message.replace(" ", "+");
		message = message.replace("?", "%3F");
		message = message.replace("=", "%3D");

		String response = request(String.format("http://api.brainshop.ai/get?bid=%s&key=%s&uid=%s&msg=%s", bid, key, id, message));
		
		Json json = Json.json();
		JsonNode root = json.parse(response);

		return root.get("cnt").asString();
	}
}
