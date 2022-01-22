package gq.glowman554.bot.http.server.api.auth;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;

import java.util.Map;

public class AuthLoginStartHandler extends HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginStartHandler(HttpApi api, String path, AuthManager authManager) {
        super(api, path);
        this.authManager = authManager;
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String login_id = authManager.random_token();
        String new_token = authManager.random_token();

        authManager.pending_tokens.put(login_id, new AuthManager.AuthLoginSession(new_token));

        return String.format("{\"id\": \"%s\", \"msg\": \"ok\"}", login_id);
    }
}
