package gq.glowman554.bot.wiki.api;

import java.util.Map;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.Pair;
import gq.glowman554.bot.wiki.event.PageCreateEvent;
import gq.glowman554.bot.wiki.event.PageDeleteEvent;
import gq.glowman554.bot.wiki.event.PageUpdateEvent;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class PageChangeLogHandler extends HttpApiHandler {
	private Pair<String, Long>[] changelog = new Pair[30];

	public PageChangeLogHandler(HttpApi api, String path) {
		super(api, path);

		for (int i = 0; i < changelog.length; i++) {
			changelog[i] = null;
		}

		load();

		EventManager.register(this);
	}

	// private void dump_changelog() {
	// 	for (int i = 0; i < changelog.length; i++) {
	// 		if (changelog[i] == null) {
	// 			break;
	// 		}

	// 		Log.log(changelog[i].t1 + " at " + changelog[i].t2);
	// 	}
	// }

	public void add_changelog_item(String what) {
		Log.log("Adding changelog item: " + what);

		int empty_index = -1;
		for (int i = 0; i < changelog.length; i++) {
			if (changelog[i] == null) {
				empty_index = i;
				break;
			}
		}

		if (empty_index == -1) {
			// remove first item
			changelog[0] = changelog[1];
			for (int i = 1; i < changelog.length - 1; i++) {
				changelog[i] = changelog[i + 1];
			}

			empty_index = changelog.length - 1;
		}

		changelog[empty_index] = new Pair<String, Long>(what, System.currentTimeMillis());

		save();
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		return Json.json().serialize(toJson());
	}

	private void save() {
		Main.configManager.set_key_as_str("wiki_changelog", toJson().toString());
	}

	private void load() {
		try {
			String json = Main.configManager.get_key_as_str("wiki_changelog");

			JsonNode root = Json.json().parse(json);

			int idx = 0;
			for (JsonNode node : root) {
				changelog[idx] = new Pair<String, Long>(node.get("what").asString(), node.get("when").asLong());
				idx++;
			}

		} catch (IllegalArgumentException | JsonSyntaxException e) {
			Log.log("Failure loading wiki changelog: " + e.getMessage());
		}
	}

	@EventTarget
	public void onUpdate(PageUpdateEvent event) {
		add_changelog_item("Page edit: " + event.page.page_title);
	}

	@EventTarget
	public void onDelete(PageDeleteEvent event) {
		add_changelog_item("Page delete: " + event.page.page_title);
	}

	@EventTarget
	public void onCreate(PageCreateEvent event) {
		add_changelog_item("Page create: " + event.page.page_title);
	}
	
	public JsonNode toJson() {
		JsonNode root = JsonNode.array();

		for (int i = 0; i < changelog.length; i++) {
			if (changelog[i] == null) {
				break;
			}

			JsonNode node = JsonNode.object();
			node.set("what", changelog[i].t1);
			node.set("when", changelog[i].t2);

			root.add(node);
		}

		return root;
	}
}