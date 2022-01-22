package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class FoxApi {
    public String _url;

    public String getFox() throws IOException, JsonSyntaxException {
        String res = HttpClient.request("https://randomfox.ca/floof/?ref=apilist.fun");

        Json json = Json.json();
        JsonNode root = json.parse(res);

        _url = root.get("image").asString();

        return _url;
    }

    public void download(String path) throws IOException {
        HttpClient.download(path, _url);
    }
}
