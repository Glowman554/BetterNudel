package gq.glowman554.bot.wiki.api;

import java.util.Map;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.wiki.PageManager;

public class PageGetHandler extends HttpApiHandler {

	public PageGetHandler(HttpApi api, String path) {
		super(api, path);
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String page_id = query.get("page_id");
		if (page_id == null) {
			return "{\"error\":\"Missing page_id\"}";
		}

		return PageManager.instance.load(page_id).toJson().toString();
	}
	
}
