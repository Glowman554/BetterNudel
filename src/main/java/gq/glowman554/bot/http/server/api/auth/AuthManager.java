package gq.glowman554.bot.http.server.api.auth;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.IOException;
import java.util.HashMap;

public class AuthManager {
    public static AuthManager instance;

    protected HashMap<String, AuthLoginSession> pending_tokens = new HashMap<>();
    protected HashMap<String, String> active_tokens = new HashMap<>();

    private String config_file = "tokens.json";

    public AuthManager(HttpApi api) throws IOException, JsonSyntaxException {
        try {
            String file_content = Main.configManager.get_key_as_str("token_config");
            Json json = Json.json();
            JsonNode root = json.parse(file_content);

            for (JsonNode node : root) {
                active_tokens.put(node.get("key").asString(), node.get("value").asString());
            }
        } catch (IllegalArgumentException e) {
            save();
        }

        new AuthLoginCheckHandler(api, "/api/v2/login/check", this);
        new AuthLoginStartHandler(api, "/api/v2/login/start", this);
        new AuthLoginStatusHandler(api, "/api/v2/login/status", this);
        new AuthLoginStopHandler(api, "/api/v2/login/stop", this);
    }

    public void add_token(String token_owner, String token) {
        active_tokens.put(token, token_owner);
        try {
            save();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    protected void save() throws IOException {
        JsonNode root = JsonNode.array();

        for (String key : active_tokens.keySet()) {
            JsonNode node = JsonNode.object();
            node.set("key", key);
            node.set("value", active_tokens.get(key));

            root.add(node);
        }

        Main.configManager.set_key_as_str("token_config", root.toString());
    }

    public String get_user_by_token(String token) {
        return active_tokens.get(token);
    }

    public String get_token_by_user(String user) {
        for (String token : active_tokens.keySet()) {
            if (active_tokens.get(token).equals(user)) {
                return token;
            }
        }

        return null;
    }

    public String random_token() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        String str = "";
        for (int i = 0; i < 10; i++) {
            str += chars.charAt((int) Math.floor(Math.random() * chars.length()));
        }

        return str;
    }

    public String checkToken(String token) {
        if (token == null) {
            return null;
        }

        if (get_user_by_token(token) == null) {
            return null;
        }

        return get_user_by_token(token);
    }

    public void ack(String login_id, String user_id) {
        pending_tokens.get(login_id).user_id = user_id;
        pending_tokens.get(login_id).ack = true;
    }

    public static class AuthLoginSession {
        public String user_id;
        public final String new_token;
        public boolean ack = false;

        public AuthLoginSession(String new_token) {
            this.new_token = new_token;
        }
    }

    public static void load(HttpApi api) throws JsonSyntaxException, IOException {
        Log.log("Loading auth handler...");
        instance = new AuthManager(api);
    }
}
