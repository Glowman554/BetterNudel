package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.utils.TimeUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.util.Map;

public class ApiUptimeHandler extends HttpApiHandler {
    public ApiUptimeHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        Json json = Json.json();

        JsonNode root = JsonNode.object();

        root.set("uptime", System.currentTimeMillis() - Main.startTime);
        root.set("uptime-sek", (System.currentTimeMillis() - Main.startTime) / 1000);
        root.set("uptime-dhms", TimeUtils.millisecond_to_dhms(System.currentTimeMillis() - Main.startTime));

        return json.serialize(root);
    }
}
