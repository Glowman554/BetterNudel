package gq.glowman554.bot.platform.web;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import net.shadew.json.Json;

import java.util.Map;

public class WebPlatformHandler extends HttpApiHandler {
    public WebPlatformHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        if (token == null) {
            return "Missing token";
        }

        String user = AuthManager.instance.checkToken(token);
        if (user == null) {
            return "Invalid token";
        }

        String message = query.get("message");
        if (message == null) {
            return "Missing message";
        }

        WebCommandEvent commandEvent = new WebCommandEvent(message, new WebCommandPlatform(), token);

        Main.commandManager.on_command(commandEvent);

        return Json.json().serialize(commandEvent.getSteps());
    }
}
