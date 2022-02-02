package gq.glowman554.bot.api;

import java.util.Map;

import gq.glowman554.bot.Exs;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import net.shadew.json.JsonNode;

public class ExsRegisterHandler extends HttpApiHandler {
	private final Exs exs;

	public ExsRegisterHandler(HttpApi api, String path, Exs exs) {
		super(api, path);
		this.exs = exs;
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String id = exs.add_client();
		JsonNode response = JsonNode.object();
		response.set("id", id);
		
		return response.toString();
	}
}
