package gq.glowman554.bot.externapi;

import java.io.IOException;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class TranslateApi {
	public static String[] languages = new String[] {
		"de",
		"en",
		"es",
		"fr",
		"it",
		"ja",
		"ko",
		"pt",
		"ru",
		"zh"
	};

	public String translate(String message, String to) throws IOException, JsonSyntaxException {
		message = message.replace("#", "%23");
		message = message.replace("&", "%26");
		message = message.replace(" ", "+");
		message = message.replace("?", "%3F");
		message = message.replace("=", "%3D");

		String response = HttpClient.get(String.format("https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=%s&dt=t&q=%s", to, message));

		Json json = Json.json();
		JsonNode root = json.parse(response);

		return root.get(0).get(0).get(0).asString();
	}
}
