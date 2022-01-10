package io.github.glowman554.nudel.httpapi.impl.auth;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;

import java.util.Map;

public class AuthLoginStatusHandler implements HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginStatusHandler(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception {
        String login_id = query.get("login_id");
        if (login_id == null) {
            return "{\"msg\": \"Please set login_id!\"}";
        }

        AuthLoginSession session = authManager.pending_tokens.get(login_id);
        if (session == null) {
            return "{\"msg\": \"Invalid login_id!\"}";
        }

        if (session.ack) {
            authManager.pending_tokens.remove(login_id);
            authManager.active_tokens.put(session.new_token, session.discord_id);
            authManager.save();

            return String.format("{\"token\": \"%s\", \"msg\": \"ok\"}", session.new_token);
        } else {
            return "{\"msg\": \"Waiting for ack!\"}";
        }
    }
}
