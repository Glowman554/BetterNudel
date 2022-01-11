package io.github.glowman554.nudel.httpapi.impl.auth;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.httpapi.HttpApi;
import io.github.glowman554.nudel.httpapi.HttpApiBaseHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AuthManager extends ListenerAdapter {
    protected HashMap<String, AuthLoginSession> pending_tokens = new HashMap<>();
    protected HashMap<String, String> active_tokens = new HashMap<>();

    private String config_file = "tokens.json";

    public AuthManager(HttpApi api) throws IOException, JsonSyntaxException {
        if (new File(config_file).exists()) {
            String file_content = FileUtils.readFile(config_file);
            Json json = Json.json();
            JsonNode root = json.parse(file_content);

            for (JsonNode node : root) {
                active_tokens.put(node.get("key").asString(), node.get("value").asString());
            }
        }

        new HttpApiBaseHandler(new AuthLoginStartHandler(this), api, "/auth/login/start");
        new HttpApiBaseHandler(new AuthLoginStatusHandler(this), api, "/auth/login/status");
        new HttpApiBaseHandler(new AuthLoginStopHandler(this), api, "/auth/login/stop");
        new HttpApiBaseHandler(new AuthLoginCheckHandler(this), api, "/auth/login/check");

        Discord.discord.jda.addEventListener(this);
    }

    protected void save() throws IOException {
        Json json = Json.json();
        JsonNode root = JsonNode.array();

        for (String key : active_tokens.keySet()) {
            JsonNode node = JsonNode.object();
            node.set("key", key);
            node.set("value", active_tokens.get(key));

            root.add(node);
        }

        FileUtils.writeFile(config_file, json.serialize(root));
    }

    public String get_user_by_token(String token) {
        return active_tokens.get(token);
    }

    public String get_token_by_user(String user) {
        for (String token : active_tokens.keySet()) {
            if (active_tokens.get(token).equals(user)) {
                return token;
            }
        }

        return null;
    }

    public String random_token() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Pick characers randomly
        String str = "";
        for (int i = 0; i < 10; i++) {
            str += chars.charAt((int) Math.floor(Math.random() * chars.length()));
        }

        return str;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.isFromType(ChannelType.PRIVATE)) {
            return;
        }

        if (event.getMessage().getContentRaw().toLowerCase().equals("yes") || event.getMessage().getContentRaw().toLowerCase().equals("no")) {
            AtomicReference<String> session_key = new AtomicReference<>(null);
            pending_tokens.forEach((key, pending_session) -> {
                if (pending_session.discord_id.equals(event.getAuthor().getId()) && !pending_session.ack) {
                    session_key.set(key);
                }
            });

            if (session_key.get() != null) {
                if (event.getMessage().getContentRaw().toLowerCase().equals("yes")) {
                    event.getChannel().sendMessage("Ack ok for: " + session_key).queue();
                    System.out.println("[discord] ack for: " + session_key.get());
                    pending_tokens.get(session_key.get()).ack = true;
                } else {
                    event.getChannel().sendMessage("Ack discard for: " + session_key).queue();
                    System.out.println("[discord] ack discard for: " + session_key.get());
                    pending_tokens.remove(session_key.get());
                }
            }
        }
    }
}
