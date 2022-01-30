package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;

import java.util.Map;

public class ApiHasPermissionHandler extends HttpApiHandler {
    public ApiHasPermissionHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "Invalid token";
        }

        String permission = query.get("permission");
        if (permission == null) {
            return "Missing permission";
        }

        return String.valueOf(Main.commandManager.permissionManager.has_permission(user, permission));
    }
}
