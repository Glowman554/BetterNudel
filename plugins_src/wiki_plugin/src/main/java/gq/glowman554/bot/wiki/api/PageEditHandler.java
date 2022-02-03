package gq.glowman554.bot.wiki.api;

import java.util.Base64;
import java.util.Map;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.wiki.Page;
import gq.glowman554.bot.wiki.PageManager;

public class PageEditHandler extends HttpApiHandler {

	public PageEditHandler(HttpApi api, String path) {
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
		if (page_title != null) {
			page_title = new String(Base64.getDecoder().decode(page_title));
		}

		String page_text = query.get("page_text");
		if (page_text != null) {
			page_text = new String(Base64.getDecoder().decode(page_text));
		}

		String page_id = query.get("page_id");
		if (page_id == null) {
			return "{\"error\":\"Missing page_id\"}";
		}

		Page current_page = PageManager.instance.load(page_id);

		if (page_title != null) {
			current_page.page_title = page_title;
		}

		if (page_text != null) {
			current_page.page_text = page_text;
		}

		PageManager.instance.edit(current_page);

		return current_page.toJson().toString();
	}
	
}
