package gq.glowman554.bot.wiki.api;

import java.util.Map;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.wiki.PageManager;
import gq.glowman554.bot.wiki.event.PageDeleteEvent;

public class PageDeleteHandler extends HttpApiHandler {

	public PageDeleteHandler(HttpApi api, String path) {
		super(api, path);
		//TODO Auto-generated constructor stub
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "{\"error\":\"Invalid token\"}";
        }

        if (!Main.commandManager.permissionManager.has_permission(user, "wiki_delete")) {
            return "{\"error\":\"You do not have permission to delete wiki pages\"}";
        }

		String page_id = query.get("page_id");
		if (page_id == null) {
			return "{\"error\":\"Missing page_id\"}";
		}

		PageDeleteEvent event = new PageDeleteEvent(PageManager.instance.load(page_id));
		event.call();

		if (event.isCanceled()) {
			return "{\"error\":\"Page deletion cancelled\"}";
		}

		PageManager.instance.delete(page_id);

		return "{\"success\":\"true\"}";
	}
}
