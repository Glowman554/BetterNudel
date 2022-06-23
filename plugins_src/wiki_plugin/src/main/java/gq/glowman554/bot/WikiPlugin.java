package gq.glowman554.bot;

import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;
import gq.glowman554.bot.utils.MultiThreadHelper;
import gq.glowman554.bot.wiki.PageManager;
import gq.glowman554.bot.wiki.api.PageChangeLogHandler;
import gq.glowman554.bot.wiki.api.PageCreateHandler;
import gq.glowman554.bot.wiki.api.PageDeleteHandler;
import gq.glowman554.bot.wiki.api.PageEditHandler;
import gq.glowman554.bot.wiki.api.PageGetHandler;
import gq.glowman554.bot.wiki.api.PageListHandler;
import gq.glowman554.bot.wiki.event.PageCreateEvent;
import gq.glowman554.bot.wiki.event.PageDeleteEvent;
import gq.glowman554.bot.wiki.event.PageUpdateEvent;

public class WikiPlugin implements Plugin {
	public static PageChangeLogHandler pageChangeLogHandlerInstance;
    @Override
    public void on_load() throws Exception {
        Log.log("Hello World!");

		EventManager.register(this);

		PageManager.load();

		new PageCreateHandler(HttpApi.instance, "/api/v2/wiki/page/create");
		new PageGetHandler(HttpApi.instance, "/api/v2/wiki/page/get");
		new PageEditHandler(HttpApi.instance, "/api/v2/wiki/page/edit");
		new PageListHandler(HttpApi.instance, "/api/v2/wiki/page/list");
		new PageDeleteHandler(HttpApi.instance, "/api/v2/wiki/page/delete");
		pageChangeLogHandlerInstance = new PageChangeLogHandler(HttpApi.instance, "/api/v2/wiki/page/changelog");


		try {
			String event_channel_id = Main.configManager.get_key_as_str("wiki_event_channel_id");

			MultiThreadHelper.run(() -> {
				Log.log("Wiki event notifier starting...");
				new DiscordEventNotifier(event_channel_id);
			});

		} catch (IllegalArgumentException ignored) {}
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
