package io.github.glowman554.nudel.httpapi.impl.auth;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.dv8tion.jda.api.entities.User;

import java.util.Map;

public class AuthLoginStopHandler implements HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginStopHandler(AuthManager authManager) {
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

        User u = Discord.discord.jda.getUserById(session.discord_id);
        if (u != null) {
            u.openPrivateChannel().complete().sendMessage("Canceled login for " + login_id + "!").queue();
        }

        authManager.pending_tokens.remove(login_id);
        return "ok";
    }
}
