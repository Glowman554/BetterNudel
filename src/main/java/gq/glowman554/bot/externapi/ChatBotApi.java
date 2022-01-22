package gq.glowman554.bot.externapi;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.client.HttpClient;
import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class ChatBotApi {
    // https://brainshop.ai/
    public String key = null;
    public String bid = null;

    public ChatBotApi() {
        try {
            key = Main.configManager.get_key_as_str("brainshop_key");
            bid = Main.configManager.get_key_as_str("brainshop_bid");
        } catch (IllegalArgumentException e) {
            Log.log("Failed to load brainshop creds!");
        }
    }

    public String response(String message, String id) throws IOException, JsonSyntaxException {
        message = message.replace("#", "%23");
        message = message.replace("&", "%26");
        message = message.replace(" ", "+");
        message = message.replace("?", "%3F");
        message = message.replace("=", "%3D");

        String response = HttpClient.request(String.format("http://api.brainshop.ai/get?bid=%s&key=%s&uid=%s&msg=%s", bid, key, id, message));

        Json json = Json.json();
        JsonNode root = json.parse(response);

        return root.get("cnt").asString();
    }
}
