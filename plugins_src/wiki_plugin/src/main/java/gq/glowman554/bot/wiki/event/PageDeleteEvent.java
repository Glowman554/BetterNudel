package gq.glowman554.bot.wiki.event;

import gq.glowman554.bot.event.EventCancelable;
import gq.glowman554.bot.wiki.Page;

public class PageDeleteEvent extends EventCancelable {
	public final Page page;

	public PageDeleteEvent(Page page) {
		this.page = page;
	}
}
