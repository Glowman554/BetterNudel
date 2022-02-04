package gq.glowman554.bot.wiki;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class PageManager {
	public static PageManager instance;

	public String[] known_pages = new String[0];

	public PageManager() {
		try {
			String known_pages_json = Main.configManager.get_key_as_str("wiki_known_pages");

			JsonNode root = Json.json().parse(known_pages_json);

			known_pages = root.asStringArray();
		} catch (IllegalArgumentException | JsonSyntaxException e) {
			Log.log(e.getClass().getSimpleName() + ": " + e.getMessage());
			Log.log("Could not load wiki_known_pages.");

			save();
		}

		Log.log("Found " + known_pages.length + " known pages.");

		// done for migration purposes
		for (String page : known_pages) {
			save(load(page));
		}
	}

	private void save() {
		JsonNode know_pages_array = JsonNode.array();
		for (String page_id : known_pages) {
			know_pages_array.add(page_id);
		}

		Main.configManager.set_key_as_str("wiki_known_pages", know_pages_array.toString());
	}

	public Page create(String title, String text) {
		Page page = Page.create(title, text);

		known_pages = ArrayUtils.add(known_pages, page.page_id);

		save();

		Main.configManager.set_key_as_str("wiki_" + page.page_id, page.toJson().toString());

		return page;
	}

	public Page load(String page_id) {
		if (!ArrayUtils.contains(known_pages, page_id)) {
			throw new IllegalArgumentException("Unknown page_id: " + page_id);
		}

		String page_json = Main.configManager.get_key_as_str("wiki_" + page_id);

		try {
			JsonNode root = Json.json().parse(page_json);

			return new Page(root);
		} catch (JsonSyntaxException e) {
			throw new IllegalArgumentException("Could not parse page_json: " + page_json);
		}
	}

	public void edit(Page page) {
		page.page_edited = System.currentTimeMillis();
		Main.configManager.set_key_as_str("wiki_" + page.page_id, page.toJson().toString());
		save();
	}

	public void save(Page page) {
		Main.configManager.set_key_as_str("wiki_" + page.page_id, page.toJson().toString());
		save();
	}


	public void delete(String page_id) {
		if (!ArrayUtils.contains(known_pages, page_id)) {
			throw new IllegalArgumentException("Unknown page_id: " + page_id);
		}

		Main.configManager.set_key_as_str("wiki_" + page_id, "");
		known_pages = ArrayUtils.remove(known_pages, page_id);

		save();
	}

	public JsonNode toJson() {
		JsonNode root = JsonNode.array();

		for (String page_id : known_pages) {
			JsonNode page_info = JsonNode.object();
			page_info.set("page_id", page_id);
			page_info.set("page_title", load(page_id).page_title);
			page_info.set("page_created", load(page_id).page_created);
			page_info.set("page_edited", load(page_id).page_edited);

			root.add(page_info);
		}

		return root;
	}

	public static void load() {
		Log.log("Loading PageManager...");
		instance = new PageManager();
	}
}
