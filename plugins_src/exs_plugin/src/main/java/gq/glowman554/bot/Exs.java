package gq.glowman554.bot;

import java.util.ArrayList;
import java.util.HashMap;

import gq.glowman554.bot.api.ExsCallbackHandler;
import gq.glowman554.bot.api.ExsCommandHandler;
import gq.glowman554.bot.api.ExsLogHandler;
import gq.glowman554.bot.api.ExsRegisterHandler;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.command.ExsCommand;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class Exs implements Plugin {
	public HashMap<String, String> command_buffer = new HashMap<>();
	public HashMap<String, String> command_callback_buffer = new HashMap<>();

	public CommandEvent last_command = null;
	
	public Exs() {
		// example command system_command||ls
		//                    Seperator  ^^

		try {
			Main.configManager.get_key_as_str("exs_config");
		} catch (IllegalArgumentException e) {
			Log.log("No exs config found, creating one...");
			Main.configManager.set_key_as_str("exs_config", "[]");
		}
	}

	public ArrayList<String> get_clients() throws JsonSyntaxException {
		ArrayList<String> result = new ArrayList<>();

		String config_file_content = Main.configManager.get_key_as_str("exs_config");
		Json json = Json.json();
		JsonNode root = json.parse(config_file_content);

		for (JsonNode node : root) {
			result.add(node.asString());
		}

		return result;
	}

	public void save_clients(ArrayList<String> clients) {
		JsonNode root = JsonNode.array();

		for (String client : clients) {
			root.add(client);
		}

		Main.configManager.set_key_as_str("exs_config", root.toString());
	}

	public String add_client() throws JsonSyntaxException {
		ArrayList<String> clients = this.get_clients();

		String id = String.format("exs-%d-%d", (int) (Math.random() * 1000000), (int) (Math.random() * 1000000));
		clients.add(id);

		this.save_clients(clients);

		return id;
	}

	public boolean is_client(String name) throws JsonSyntaxException {
		ArrayList<String> clients = this.get_clients();

		return clients.contains(name);
	}
	
	@Override
	public void on_load() throws Exception {
		Log.log("Hello big gay world!");

		new ExsLogger();

		new ExsCallbackHandler(HttpApi.instance, "/api/v2/exs/callback", this);
		new ExsCommandHandler(HttpApi.instance, "/api/v2/exs/command", this);
		new ExsRegisterHandler(HttpApi.instance, "/api/v2/exs/register", this);
		new ExsLogHandler(HttpApi.instance, "/api/v2/exs/log");

		Main.commandManager.add_command("exs", new ExsCommand(this));
	}
}
