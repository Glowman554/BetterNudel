package gq.glowman554.bot.wiki.api;

import java.util.Base64;
import java.util.Map;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.wiki.Page;
import gq.glowman554.bot.wiki.PageManager;
import gq.glowman554.bot.wiki.event.PageCreateEvent;

public class PageCreateHandler extends HttpApiHandler {

	public PageCreateHandler(HttpApi api, String path) {
		super(api, path);
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
        String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "{\"error\":\"Invalid token\"}";
        }

        if (!Main.commandManager.permissionManager.has_permission(user, "wiki_editor")) {
            return "{\"error\":\"You do not have permission to edit wiki pages\"}";
        }

		String page_title = query.get("page_title");
		if (page_title == null) {
			return "{\"error\":\"Missing page_title\"}";
		}
		page_title = new String(Base64.getDecoder().decode(page_title));

		String page_text = query.get("page_text");
		if (page_text == null) {
			return "{\"error\":\"Missing page_text\"}";
		}
		page_text = new String(Base64.getDecoder().decode(page_text));

		Page page =  PageManager.instance.create(page_title, page_text);

		new PageCreateEvent(page).call();

		return page.toJson().toString();
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers, String body) throws Exception {
		String token = query.get("token");
        String user = AuthManager.instance.checkToken(token);

        if (user == null) {
            return "{\"error\":\"Invalid token\"}";
        }

        if (!Main.commandManager.permissionManager.has_permission(user, "wiki_editor")) {
            return "{\"error\":\"You do not have permission to edit wiki pages\"}";
        }

		String page_title = query.get("page_title");
		if (page_title == null) {
			return "{\"error\":\"Missing page_title\"}";
		}
		page_title = new String(Base64.getDecoder().decode(page_title));

		String page_text = new String(Base64.getDecoder().decode(body));

		Page page =  PageManager.instance.create(page_title, page_text);

		new PageCreateEvent(page).call();

		return page.toJson().toString();
	}
}
