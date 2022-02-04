package gq.glowman554.bot;

import java.net.URLDecoder;

import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.wiki.event.PageCreateEvent;
import gq.glowman554.bot.wiki.event.PageDeleteEvent;
import gq.glowman554.bot.wiki.event.PageUpdateEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscordEventNotifier {
	private final TextChannel textChannel;
	private final String channel_id;

	public DiscordEventNotifier(String channel_id) {
		this.channel_id = channel_id;

		textChannel = Main.discordPlatform.jda.getTextChannelById(channel_id);

		if (textChannel == null) {
			throw new IllegalArgumentException("Channel not found");
		}

		EventManager.register(this);
	}

	private void build_and_send_update(String type, String page_title) {
		page_title = URLDecoder.decode(page_title);
		EmbedBuilder eb = new EmbedBuilder();

		eb.setTitle(type);
		eb.setDescription(page_title + ": " + type);
		eb.setColor(0x00FF00);

		textChannel.sendMessage(eb.build()).queue();
	}

	@EventTarget
	public void onUpdate(PageUpdateEvent event) {
		build_and_send_update("Page edit", event.page.page_title);
	}

	@EventTarget
	public void onDelete(PageDeleteEvent event) {
		build_and_send_update("Page delete", event.page.page_title);
	}

	@EventTarget
	public void onCreate(PageCreateEvent event) {
		build_and_send_update("Page create", event.page.page_title);
	}
}
