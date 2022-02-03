package gq.glowman554.bot;

import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;
import gq.glowman554.bot.wiki.PageManager;
import gq.glowman554.bot.wiki.api.PageCreateHandler;
import gq.glowman554.bot.wiki.api.PageEditHandler;
import gq.glowman554.bot.wiki.api.PageGetHandler;
import gq.glowman554.bot.wiki.event.PageCreateEvent;
import gq.glowman554.bot.wiki.event.PageDeleteEvent;
import gq.glowman554.bot.wiki.event.PageUpdateEvent;

public class WikiPlugin implements Plugin {
    @Override
    public void on_load() throws Exception {
        Log.log("Hello World!");

		EventManager.register(this);

		PageManager.load();

		new PageCreateHandler(HttpApi.instance, "/api/v2/wiki/page/create");
		new PageGetHandler(HttpApi.instance, "/api/v2/wiki/page/get");
		new PageEditHandler(HttpApi.instance, "/api/v2/wiki/page/edit");
    }

	@EventTarget
	public void onUpdate(PageUpdateEvent event) {
		Log.log(event.page.toString());
	}

	@EventTarget
	public void onDelete(PageDeleteEvent event) {
		Log.log(event.page.toString());
	}

	@EventTarget
	public void onCreate(PageCreateEvent event) {
		Log.log(event.page.toString());
	}
}
