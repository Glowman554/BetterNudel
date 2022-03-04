package gq.glowman554.bot.externapi;

import gq.glowman554.bot.http.client.HttpClient;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;

public class WikipediaApi {
    public String searchWiki(String query) throws IOException, JsonSyntaxException {
        query = query.replaceAll(" ", "%20");

        String res = HttpClient.get("https://en.wikipedia.org/api/rest_v1/page/summary/" + query);

        Json json = Json.json();
        JsonNode root = json.parse(res);

        return root.get("extract").asString();
    }
}
