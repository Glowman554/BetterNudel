package gq.glowman554.bot.api;

import java.util.Map;

import gq.glowman554.bot.Exs;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import net.shadew.json.JsonNode;

public class ExsCommandHandler extends HttpApiHandler {
	private final Exs exs;
	private final String path;

	public ExsCommandHandler(HttpApi api, String path, Exs exs) {
		super(api, path);
		this.path = path;
		this.exs = exs;
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String id = query.get("id");
		if (id == null) {
			return "{\"msg\": \"missing id\"}";
		}

		if (!exs.get_clients().contains(id)) {
			return "{\"msg\": \"unknown client call " + this.path + " first!\"}";
		} else {
			JsonNode root = JsonNode.array();

			for (String key : exs.command_buffer.keySet()) {
				JsonNode node = JsonNode.object();
				node.set("key", key);
				node.set("value", exs.command_buffer.get(key));

				root.add(node);
			}

			return root.toString();
		}
	}
}
