package gq.glowman554.bot.wiki;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonNode;

public class Page {
	public String page_id;
	public String page_title;
	public String page_text;
	public Long page_created;
	public Long page_edited;

	public Page(String page_id, String page_title, String page_text, Long page_created, Long page_edited) {
		this.page_id = page_id;
		this.page_title = page_title;
		this.page_text = page_text;
		this.page_created = page_created;
		this.page_edited = page_edited;
	}

	public Page(JsonNode root) {
		page_id = root.get("page_id").asString();
		page_title = root.get("page_title").asString();
		page_text = root.get("page_text").asString();

		if (root.get("page_created") == null) {
			Log.log("Migration: page_created is null setting to current time");
			page_created = System.currentTimeMillis();
		} else {
			page_created = root.get("page_created").asLong();
		}

		if (root.get("page_edited") == null) {
			Log.log("Migration: page_edited is null setting to current time");
			page_edited = System.currentTimeMillis();
		} else {
			page_edited = root.get("page_edited").asLong();
		}
	}

	public JsonNode toJson() {
		JsonNode root = JsonNode.object();

		root.set("page_id", page_id);
		root.set("page_title", page_title);
		root.set("page_text", page_text);
		root.set("page_created", page_created);
		root.set("page_edited", page_edited);

		return root;
	}

	public static Page create(String page_title, String page_text) {
		String page_id = String.format("%d_%d", (int) (Math.random() * 1000000), (int) (Math.random() * 1000000));

		return new Page(page_id, page_title, page_text, System.currentTimeMillis(), System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return String.format("Page [page_id=%s, page_title=%s, page_text=%s, page_created=%d]", page_id, page_title, page_text, page_created);
	}
}
