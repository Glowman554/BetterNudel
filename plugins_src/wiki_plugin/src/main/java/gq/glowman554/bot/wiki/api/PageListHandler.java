package gq.glowman554.bot.wiki.api;

import java.util.Map;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.wiki.PageManager;
import net.shadew.json.Json;

public class PageListHandler extends HttpApiHandler {

	public PageListHandler(HttpApi api, String path) {
		super(api, path);
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		return Json.json().serialize(PageManager.instance.toJson());
	}
}
