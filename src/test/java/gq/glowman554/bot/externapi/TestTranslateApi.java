package gq.glowman554.bot.externapi;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import gq.glowman554.bot.log.Log;
import net.shadew.json.JsonSyntaxException;

public class TestTranslateApi {
	@Test
	public void test1() throws IOException, JsonSyntaxException {
		TranslateApi api = new TranslateApi();

		String response = api.translate("Hallo wie heißen sie?", "ja");

		Log.log(response);
	}

	@Test
	public void test2() throws IOException, JsonSyntaxException {
		TranslateApi api = new TranslateApi();

		for (String lang : TranslateApi.languages) {
			String response = api.translate("Hallo wie heißen sie?", lang);

			Log.log(response);
		}
	}
}
