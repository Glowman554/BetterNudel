package io.github.glowman554.nudel;

import java.io.IOException;

import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.impl.CatCommand;
import io.github.glowman554.nudel.discord.commands.impl.CoinflipCommand;
import io.github.glowman554.nudel.discord.commands.impl.CommitCommand;
import io.github.glowman554.nudel.discord.commands.impl.CoronaCommand;
import io.github.glowman554.nudel.discord.commands.impl.DogCommand;
import io.github.glowman554.nudel.discord.commands.impl.ExecCommand;
import io.github.glowman554.nudel.discord.commands.impl.FactCommand;
import io.github.glowman554.nudel.discord.commands.impl.FoxCommand;
import io.github.glowman554.nudel.discord.commands.impl.FurryCommand;
import io.github.glowman554.nudel.discord.commands.impl.Im18Command;
import io.github.glowman554.nudel.discord.commands.impl.JokeCommand;
import io.github.glowman554.nudel.discord.commands.impl.MemeCommand;
import io.github.glowman554.nudel.discord.commands.impl.NickCommand;
import io.github.glowman554.nudel.discord.commands.impl.PingCommand;
import io.github.glowman554.nudel.discord.commands.impl.PronounDbCommand;
import io.github.glowman554.nudel.discord.commands.impl.RepeatCommand;
import io.github.glowman554.nudel.discord.commands.impl.RoleCommand;
import io.github.glowman554.nudel.discord.commands.impl.SayCommand;
import io.github.glowman554.nudel.discord.commands.impl.StatusCommand;
import io.github.glowman554.nudel.discord.commands.impl.TtsCommand;
import io.github.glowman554.nudel.discord.commands.impl.WikipediaCommand;
import io.github.glowman554.nudel.discord.commands.impl.YiffCommand;
import io.github.glowman554.nudel.httpapi.HttpApi;
import io.github.glowman554.nudel.httpapi.HttpApiBaseHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiCollectHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiMessageHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiPermsHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiScienceHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiStatusHandler;
import io.github.glowman554.nudel.httpapi.impl.ApiSuggestHandler;
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
	public static ArgParser parser;

	public static void main(String[] args) throws Exception
	{
		parser = new ArgParser(args);
		parser.parse();

		String token;
		String application_id;
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

		try
		{
			String _url = parser.consume_option("--download-science");
			String perms = new BaseApi().request(_url);

			Json _json = Json.json();
			JsonNode _root = _json.parse(perms);

			FileUtils.writeFile("science.json", perms);

			System.out.println("Downloaded science");
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
			application_id = System.getenv("DISCORD_APP_ID");
			port = Integer.parseInt(System.getenv("PORT"));
		}
		else
		{

			String config_file = parser.consume_option("--config", "config.json");
			String config = FileUtils.readFile(config_file);

			Json config_json = Json.json();
			JsonNode config_root = config_json.parse(config);

			token = config_root.get("token").asString();
			application_id = config_root.get("application_id").asString();
		}

		Discord.init(token, application_id);

		Discord.discord.receiver.tiny_crash_report = parser.is_option("--tiny-crash");

		http_api = new HttpApi(port);

		HttpApiBaseHandler root_path = new HttpApiBaseHandler(new RootHttpHandler(), http_api, "/");
		HttpApiBaseHandler api_perms_path = new HttpApiBaseHandler(new ApiPermsHandler(), http_api, "/api/perms");
		HttpApiBaseHandler api_status_path = new HttpApiBaseHandler(new ApiStatusHandler(), http_api, "/api/status");
		HttpApiBaseHandler api_collect_path = new HttpApiBaseHandler(new ApiCollectHandler("science.json"), http_api, "/api/collect");
		HttpApiBaseHandler api_science_path = new HttpApiBaseHandler(new ApiScienceHandler(), http_api, "/api/science");
		HttpApiBaseHandler api_suggest_path = new HttpApiBaseHandler(new ApiSuggestHandler(), http_api, "/api/suggest");
		HttpApiBaseHandler api_message_path = new HttpApiBaseHandler(new ApiMessageHandler(), http_api, "/api/message");

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
		Discord.discord.commandManager.addCommand("fact", new FactCommand());
		Discord.discord.commandManager.addCommand("joke", new JokeCommand());
		Discord.discord.commandManager.addCommand("wikipedia", new WikipediaCommand());
		Discord.discord.commandManager.addCommand("tts", new TtsCommand());
		Discord.discord.commandManager.addCommand("pronoun", new PronounDbCommand());
		Discord.discord.commandManager.addCommand("corona", new CoronaCommand());
		Discord.discord.commandManager.addCommand("exec", new ExecCommand());
		Discord.discord.commandManager.addCommand("role", new RoleCommand());

		Discord.discord.commandManager.addSlashCommand("say", new SayCommand());
		Discord.discord.commandManager.addSlashCommand("cat", new CatCommand());
		Discord.discord.commandManager.addSlashCommand("coinflip", new CoinflipCommand());
		Discord.discord.commandManager.addSlashCommand("commit", new CommitCommand());
		Discord.discord.commandManager.addSlashCommand("corona", new CoronaCommand());
		Discord.discord.commandManager.addSlashCommand("dog", new DogCommand());
		Discord.discord.commandManager.addSlashCommand("exec", new ExecCommand());
		Discord.discord.commandManager.addSlashCommand("fact", new FactCommand());
		Discord.discord.commandManager.addSlashCommand("fox", new FoxCommand());
		Discord.discord.commandManager.addSlashCommand("furry", new FurryCommand());
		Discord.discord.commandManager.addSlashCommand("im_18", new Im18Command());
		Discord.discord.commandManager.addSlashCommand("joke", new JokeCommand());
		Discord.discord.commandManager.addSlashCommand("meme", new MemeCommand());
		Discord.discord.commandManager.addSlashCommand("nick", new NickCommand());
		Discord.discord.commandManager.addSlashCommand("ping", new PingCommand());
		Discord.discord.commandManager.addSlashCommand("pronoun", new PronounDbCommand());
		Discord.discord.commandManager.addSlashCommand("role", new RoleCommand());
		Discord.discord.commandManager.addSlashCommand("status", new StatusCommand());
		Discord.discord.commandManager.addSlashCommand("tts", new TtsCommand());
		Discord.discord.commandManager.addSlashCommand("wikipedia", new WikipediaCommand());
		Discord.discord.commandManager.addSlashCommand("yiff", new YiffCommand());

		pluginsLoader = new PluginsLoader("plugins");
		pluginsLoader.load_all();
	}
}
