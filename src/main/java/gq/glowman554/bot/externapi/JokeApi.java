package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class JokeApi {
    public String getJoke() throws IOException, JsonSyntaxException {
        String res = HttpClient.get("http://api.icndb.com/jokes/random");
        Json json = Json.json();
        JsonNode root = json.parse(res);

        return root.get("value").get("joke").asString();
    }
}
