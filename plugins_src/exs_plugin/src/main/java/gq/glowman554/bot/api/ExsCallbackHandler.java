package gq.glowman554.bot.api;

import java.util.Base64;
import java.util.Map;

import gq.glowman554.bot.Exs;
import gq.glowman554.bot.event.ExsCallbackEvent;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;

public class ExsCallbackHandler extends HttpApiHandler{
	private final Exs exs;

	public ExsCallbackHandler(HttpApi api, String path, Exs exs) {
		super(api, path);
		this.exs = exs;
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String id = query.get("id");
		if (id == null) {
			return "{\"msg\": \"missing id\"}";
		}

		String callback_id = query.get("cid");

		if (callback_id == null) {
			return "{\"msg\": \"missing cid\"}";
		}

		String text = query.get("text");
		if (text != null) {
			text = new String(Base64.getDecoder().decode(text));
		} else {
			text = String.format("%s completed command with callback id %s!", id, callback_id);
		}

		if (exs.command_callback_buffer.get(id) != null) {
			if (exs.command_callback_buffer.get(id).equals(callback_id)) {
				new ExsCallbackEvent(callback_id, id, text).call();

				if (exs.last_command != null) {
					exs.last_command.message_send(String.format("[%s]\n%s", id, exs.last_command.command_platform.format_code_block(text)));
				}

				exs.command_callback_buffer.remove(id);
				return "{\"msg\": \"it do be working!\"}";

			} else {
				return "{\"msg\": \"unexpected callback\"}";
			}
		} else {
			return "{\"msg\": \"unexpected callback\"}";
		}
	}
	
}
