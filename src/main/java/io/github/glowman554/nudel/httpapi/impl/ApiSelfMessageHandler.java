package io.github.glowman554.nudel.httpapi.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.httpapi.impl.auth.AuthManager;
import io.github.glowman554.nudel.utils.TokenUtils;
import net.dv8tion.jda.api.entities.User;

import java.util.Map;

public class ApiSelfMessageHandler implements HttpApiHandler {
    private final AuthManager authManager;

    public ApiSelfMessageHandler(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception {
        String token = query.get("login_token");
        String user = TokenUtils.checkToken(token, authManager);

        String body = query.get("body");
        if (body == null) {
            return "Missing body";
        }

        User u = Discord.discord.jda.getUserById(user);
        if (u == null) {
            return "Unknown error";
        }

        u.openPrivateChannel().complete().sendMessage(body).queue();

        return "Message sent";
    }
}
