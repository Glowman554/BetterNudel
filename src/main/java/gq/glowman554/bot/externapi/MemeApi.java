package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class MemeApi {
    public String _url;

    public String getMeme() throws IOException, JsonSyntaxException {
        String res = HttpClient.get("https://meme-api.herokuapp.com/gimme");

        Json json = Json.json();
        JsonNode root = json.parse(res);

        _url = root.get("url").asString();

        return _url;
    }

    public void download(String path) throws IOException {
        HttpClient.download(path, _url);
    }
}
