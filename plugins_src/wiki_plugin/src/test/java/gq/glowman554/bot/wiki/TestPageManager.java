package gq.glowman554.bot.wiki;

import org.junit.jupiter.api.Test;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;

public class TestPageManager {
	@Test
	public void test1() {
		Main.load_config("test.json");

		PageManager.load();

		Page page = PageManager.instance.create("test", "test bla bla");
		Log.log(Json.json().serialize(page.toJson()));

		Page page2 = PageManager.instance.load(page.page_id);
		Log.log(Json.json().serialize(page2.toJson()));

		page2.page_title = "test2";
		PageManager.instance.edit(page2);

		Page page3 = PageManager.instance.load(page.page_id);
		Log.log(Json.json().serialize(page3.toJson()));
	}
}
