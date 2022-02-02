package gq.glowman554.bot.api;

import java.util.Base64;
import java.util.Map;

import gq.glowman554.bot.event.ExsLogEvent;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;

public class ExsLogHandler extends HttpApiHandler {

	public ExsLogHandler(HttpApi api, String path) {
		super(api, path);
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String text = query.get("text");
		if (text == null) {
			return "{\"msg\": \"missing text\"}";
		}

		text = new String(Base64.getDecoder().decode(text));

		String id = query.get("id");

		new ExsLogEvent(text, id).call();
		
		return "{\"msg\": \"it do be working!\"}";
	}
	
}
