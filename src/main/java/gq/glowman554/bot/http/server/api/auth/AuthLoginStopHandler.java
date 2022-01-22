package gq.glowman554.bot.http.server.api.auth;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;

import java.util.Map;

public class AuthLoginStopHandler extends HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginStopHandler(HttpApi api, String path, AuthManager authManager) {
        super(api, path);
        this.authManager = authManager;
    }


    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String login_id = query.get("login_id");
        if (login_id == null) {
            return "{\"msg\": \"Please set login_id!\"}";
        }

        AuthManager.AuthLoginSession session = authManager.pending_tokens.get(login_id);
        if (session == null) {
            return "{\"msg\": \"Invalid login_id!\"}";
        }

        authManager.pending_tokens.remove(login_id);
        return "{\"msg\": \"ok\"}";
    }
}
