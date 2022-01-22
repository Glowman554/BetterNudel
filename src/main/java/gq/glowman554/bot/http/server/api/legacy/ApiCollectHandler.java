package gq.glowman554.bot.http.server.api.legacy;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.util.Map;

public class ApiCollectHandler extends HttpApiHandler {
    private final Json json = Json.json();
    private JsonNode root;

    public ApiCollectHandler(HttpApi api, String path) {
        super(api, path);

        try {
            root = json.parse(Main.configManager.get_key_as_str("api_collect_legacy"));
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            Log.log("Making default config...");

            root = JsonNode.object();
            root.set("num_visits", 0);
            root.set("last_visit", 0);
            root.set("known_agents", JsonNode.array());

            Main.configManager.set_key_as_str("api_collect_legacy", root.toString());
        }
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String user_agent = headers.get("user-agent");

        root.set("num_visits", root.get("num_visits").asInt() + 1);
        root.set("last_visit", System.currentTimeMillis());

        JsonNode known_agents = root.get("known_agents");

        boolean found = false;

        for (String agent : known_agents.asStringArray()) {
            if (agent.equals(user_agent)) {
                found = true;
                break;
            }
        }

        if (!found) {
            known_agents.add(user_agent);
        }

        Main.configManager.set_key_as_str("api_collect_legacy", root.toString());

        return json.serialize(root);
    }
}
