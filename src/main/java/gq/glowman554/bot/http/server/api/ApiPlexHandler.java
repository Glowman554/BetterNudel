package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;

import java.util.Base64;
import java.util.Map;

public class ApiPlexHandler extends HttpApiHandler {
    public ApiPlexHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "Invalid token";
        }

        switch (query.get("event")) {
            case "stream_stop": {
                Main.discordPlatform.setDefaultRP();
            }
            break;

            case "stream_progress": {
                Main.discordPlatform.setRP(String.format("%s on plex!", new String(Base64.getDecoder().decode(query.get("title")))));
            }
            break;

            default:
                return "what?";
        }

        return "ok";
    }
}
