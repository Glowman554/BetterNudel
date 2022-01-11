package io.github.glowman554.nudel.httpapi.impl.auth;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import net.dv8tion.jda.api.entities.User;

import java.util.Map;

public class AuthLoginStartHandler implements HttpApiHandler {
    private final AuthManager authManager;

    public AuthLoginStartHandler(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String execute(Map<String, String> query) throws Exception {
        String discord_id = query.get("discord_id");
        if (discord_id == null) {
            return "{\"msg\": \"Please set discord_id\"}";
        }

        User u = Discord.discord.jda.getUserByTag(discord_id);

        if (u == null) {
            return "{\"msg\": \"Failed to fetch user! Make sure you have the right tag!\"}";
        }

        String new_token;
        if (authManager.get_token_by_user(u.getId()) != null) {
            new_token = authManager.get_token_by_user(u.getId());
        } else {
            new_token = authManager.random_token();
        }

        String login_id = authManager.random_token();

        authManager.pending_tokens.put(login_id, new AuthLoginSession(u.getId(), new_token));

        u.openPrivateChannel().complete().sendMessage(String.format("Login attempt from %s! Send 'yes' to accept it or 'no' to discard it!!", query.get("ip"))).queue();

        return String.format("{\"id\": \"%s\", \"msg\": \"ok\"}", login_id);
    }
}
