package gq.glowman554.bot.wiki;

import net.shadew.json.JsonNode;

public class Page {
	public String page_id;
	public String page_title;
	public String page_text;

	public Page(String page_id, String page_title, String page_text) {
		this.page_id = page_id;
		this.page_title = page_title;
		this.page_text = page_text;
	}

	public Page(JsonNode root) {
		page_id = root.get("page_id").asString();
		page_title = root.get("page_title").asString();
		page_text = root.get("page_text").asString();
	}

	public JsonNode toJson() {
		JsonNode root = JsonNode.object();

		root.set("page_id", page_id);
		root.set("page_title", page_title);
		root.set("page_text", page_text);

		return root;
	}

	public static Page create(String page_title, String page_text) {
		String page_id = String.format("%d_%d", (int) (Math.random() * 1000000), (int) (Math.random() * 1000000));

		return new Page(page_id, page_title, page_text);
	}

	@Override
	public String toString() {
		return String.format("Page [page_id=%s, page_title=%s, page_text=%s]", page_id, page_title, page_text);
	}
}
