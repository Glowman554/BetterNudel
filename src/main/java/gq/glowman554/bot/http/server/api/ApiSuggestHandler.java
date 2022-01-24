package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.util.Map;

public class ApiSuggestHandler extends HttpApiHandler {
    private JsonNode config;

    public ApiSuggestHandler(HttpApi api, String path) {
        super(api, path);
        load();
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String suggestion = query.get("suggestion");
        if (suggestion == null) {
            return "Missing suggestion";
        }

        load();
        config.add(suggestion);
        save();

        return "ok";
    }

    private void load() {
        Log.log("Loading config...");
        try {
            String config_s = Main.configManager.get_key_as_str("suggestions");
            config = Json.json().parse(config_s);
        } catch (IllegalArgumentException | JsonSyntaxException e) {
            Log.log(e.getMessage());
            config = JsonNode.array();
        }
    }

    private void save() {
        Log.log("Saving config...");

        Main.configManager.set_key_as_str("suggestions", config.toString());
    }
}
