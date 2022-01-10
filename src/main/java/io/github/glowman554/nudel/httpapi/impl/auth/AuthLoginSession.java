package io.github.glowman554.nudel.httpapi.impl.auth;

public class AuthLoginSession {
    public final String discord_id;
    public final String new_token;
    public boolean ack = false;

    public AuthLoginSession(String discord_id, String new_token) {
        this.discord_id = discord_id;
        this.new_token = new_token;
    }
}
