package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import net.shadew.json.Json;

import java.util.Map;

public class ApiCommandsHandler extends HttpApiHandler {
    public ApiCommandsHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        return Json.json().serialize(Main.commandManager.toJson());
    }
}
