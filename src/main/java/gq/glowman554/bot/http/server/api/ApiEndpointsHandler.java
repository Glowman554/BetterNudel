package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import net.shadew.json.Json;

import java.util.Map;

public class ApiEndpointsHandler extends HttpApiHandler {
    public ApiEndpointsHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        return Json.json().serialize(api.toJson());
    }
}
