package gq.glowman554.bot.http.server.api.legacy;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import net.shadew.json.Json;

import java.util.Map;

public class ApiScienceHandler extends HttpApiHandler {
    private Json json = Json.json();

    public ApiScienceHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        return json.serialize(json.parse(Main.configManager.get_key_as_str("api_collect_legacy")));
    }
}
