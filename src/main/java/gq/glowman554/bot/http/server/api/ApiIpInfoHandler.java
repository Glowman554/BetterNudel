package gq.glowman554.bot.http.server.api;

import gq.glowman554.bot.externapi.IpInfoApi;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import net.shadew.json.Json;

import java.util.Map;

public class ApiIpInfoHandler extends HttpApiHandler {
    public ApiIpInfoHandler(HttpApi api, String path) {
        super(api, path);
    }

    @Override
    public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String ip = query.get("ip");
        if (ip == null) {
            return "Missing ip";
        }

        return Json.json().serialize(new IpInfoApi().request_info(ip).toJson());
    }
}
