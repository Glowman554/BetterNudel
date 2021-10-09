package io.github.glowman554.nudel;

import java.io.IOException;

import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.impl.CatCommand;
import io.github.glowman554.nudel.discord.commands.impl.CoinflipCommand;
import io.github.glowman554.nudel.discord.commands.impl.CommitCommand;
import io.github.glowman554.nudel.discord.commands.impl.DogCommand;
import io.github.glowman554.nudel.discord.commands.impl.FoxCommand;
import io.github.glowman554.nudel.discord.commands.impl.FurryCommand;
import io.github.glowman554.nudel.discord.commands.impl.Im18Command;
import io.github.glowman554.nudel.discord.commands.impl.MemeCommand;
import io.github.glowman554.nudel.discord.commands.impl.NickCommand;
import io.github.glowman554.nudel.discord.commands.impl.PingCommand;
import io.github.glowman554.nudel.discord.commands.impl.RepeatCommand;
import io.github.glowman554.nudel.discord.commands.impl.SayCommand;
import io.github.glowman554.nudel.discord.commands.impl.StatusCommand;
import io.github.glowman554.nudel.discord.commands.impl.YiffCommand;
import io.github.glowman554.nudel.httpapi.HttpApi;
import io.github.glowman554.nudel.httpapi.HttpApiBaseHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiPermsHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiStatusHandler;
import io.github.glowman554.nudel.httpapi.impl.RootHttpHandler;
import io.github.glowman554.nudel.plugin.PluginsLoader;
import io.github.glowman554.nudel.utils.ArgParser;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

@SuppressWarnings("unused")
public class Main {

	public static HttpApi http_api;
	public static PluginsLoader pluginsLoader;
	public static ArgParser argParser;

	public static void main(String[] args) throws Exception
	{
		ArgParser parser = new ArgParser(args);
		parser.parse();

		String token;
		int port = 8888;

		try
		{
			String _url = parser.consume_option("--download-perms");
			String perms = new BaseApi().request(_url);

			Json _json = Json.json();
			JsonNode _root = _json.parse(perms);

			FileUtils.writeFile("perms.json", perms);

			System.out.println("Downloaded perms");
			System.exit(0);
		}
		catch (IllegalArgumentException e)
		{

		}
		catch (IOException|JsonSyntaxException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		if (parser.is_option("--no-cfg"))
		{
			token = System.getenv("DISCORD_TOKEN");
			port = Integer.parseInt(System.getenv("PORT"));
		}
		else
		{

			String config_file = parser.consume_option("--config", "config.json");
			String config = FileUtils.readFile(config_file);

			Json config_json = Json.json();
			JsonNode config_root = config_json.parse(config);

			token = config_root.get("token").asString();
		}

		Discord.init(token);

		http_api = new HttpApi(port);

		HttpApiBaseHandler root_path = new HttpApiBaseHandler(new RootHttpHandler(), http_api, "/");
		HttpApiBaseHandler api_perms_path = new HttpApiBaseHandler(new ApiPermsHandler(), http_api, "/api/perms");
		HttpApiBaseHandler api_status_path = new HttpApiBaseHandler(new ApiStatusHandler(), http_api, "/api/status");

		Discord.discord.commandManager.addCommand("ping", new PingCommand());
		Discord.discord.commandManager.addCommand("furry", new FurryCommand());
		Discord.discord.commandManager.addCommand("yiff", new YiffCommand());
		Discord.discord.commandManager.addCommand("im_18", new Im18Command());
		Discord.discord.commandManager.addCommand("repeat", new RepeatCommand());
		Discord.discord.commandManager.addCommand("cat", new CatCommand());
		Discord.discord.commandManager.addCommand("dog", new DogCommand());
		Discord.discord.commandManager.addCommand("fox", new FoxCommand());
		Discord.discord.commandManager.addCommand("coinflip", new CoinflipCommand());
		Discord.discord.commandManager.addCommand("nick", new NickCommand());
		Discord.discord.commandManager.addCommand("say", new SayCommand());
		Discord.discord.commandManager.addCommand("meme", new MemeCommand());
		Discord.discord.commandManager.addCommand("commit", new CommitCommand());
		Discord.discord.commandManager.addCommand("status", new StatusCommand());

		pluginsLoader = new PluginsLoader("plugins");
		pluginsLoader.load_all();
	}
}
