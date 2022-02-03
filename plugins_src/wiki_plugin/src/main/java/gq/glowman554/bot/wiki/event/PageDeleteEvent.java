package gq.glowman554.bot.wiki.event;

import gq.glowman554.bot.event.Event;
import gq.glowman554.bot.wiki.Page;

public class PageDeleteEvent extends Event {
	public final Page page;

	public PageDeleteEvent(Page page) {
		this.page = page;
	}
}
