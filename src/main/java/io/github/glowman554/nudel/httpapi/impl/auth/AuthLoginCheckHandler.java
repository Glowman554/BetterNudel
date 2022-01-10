package io.github.glowman554.nudel.httpapi.impl.auth;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.TokenUtils;

import java.util.Map;

public class AuthLoginCheckHandler implements HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginCheckHandler(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception {
        String token = query.get("login_token");
        String user = TokenUtils.checkToken(token, authManager);

        return "OK";
    }
}
