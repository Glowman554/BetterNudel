package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;

import java.util.Map;

public class ApiSuggestionsDeleteHandler extends HttpApiHandler {
    public ApiSuggestionsDeleteHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "Invalid token";
        }

        if (!Main.commandManager.permissionManager.has_permission(user, "suggestions")) {
            return "You cant use this api endpoint!";
        }

        Main.configManager.set_key_as_str("suggestions", "[]");

        return "ok";
    }
}
