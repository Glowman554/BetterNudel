package gq.glowman554.bot.http.server.api.auth;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;

import java.util.Map;

public class AuthLoginCheckHandler extends HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginCheckHandler(HttpApi api, String path, AuthManager authManager) {
        super(api, path);
        this.authManager = authManager;
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = authManager.checkToken(token);

        if (user == null) {
            return "{\"msg\": \"fail\"}";
        } else {
            return "{\"msg\": \"ok\"}";
        }
    }
}
