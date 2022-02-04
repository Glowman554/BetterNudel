package gq.glowman554.bot.wiki;

import org.junit.jupiter.api.Test;

import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;

public class TestPage {
	@Test
	public void test1() {
		Page page = new Page("page_id", "page_title", "page_text", 0l, 0l);
		Log.log(Json.json().serialize(page.toJson()));
		
		Page page2 = Page.create("page_title", "page_text");
		Log.log(Json.json().serialize(page2.toJson()));
	}
}
