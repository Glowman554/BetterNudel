package io.github.glowman554.nudel;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.impl.CatCommand;
import io.github.glowman554.nudel.discord.commands.impl.ChatBotCommand;
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
import io.github.glowman554.nudel.discord.commands.impl.LoadPluginCommand;
import io.github.glowman554.nudel.discord.commands.impl.MemeCommand;
import io.github.glowman554.nudel.discord.commands.impl.NickCommand;
import io.github.glowman554.nudel.discord.commands.impl.PingCommand;
import io.github.glowman554.nudel.discord.commands.impl.PronounDbCommand;
import io.github.glowman554.nudel.discord.commands.impl.RepeatCommand;
import io.github.glowman554.nudel.discord.commands.impl.RoleCommand;
import io.github.glowman554.nudel.discord.commands.impl.SayCommand;
import io.github.glowman554.nudel.discord.commands.impl.StatusCommand;
import io.github.glowman554.nudel.discord.commands.impl.TtsCommand;
import io.github.glowman554.nudel.discord.commands.impl.UploadCommand;
import io.github.glowman554.nudel.discord.commands.impl.WikipediaCommand;
import io.github.glowman554.nudel.discord.commands.impl.YiffCommand;
import io.github.glowman554.nudel.exs.Exs;
import io.github.glowman554.nudel.httpapi.HttpApi;
import io.github.glowman554.nudel.httpapi.HttpApiBaseHandler;
import io.github.glowman554.nudel.httpapi.impl.*;
import io.github.glowman554.nudel.httpapi.impl.auth.AuthManager;
import io.github.glowman554.nudel.plugin.PluginsLoader;
import io.github.glowman554.nudel.utils.ArgParser;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

@SuppressWarnings("unused")
public class Main {

	public static HttpApi http_api;
	public static AuthManager authManager;
	public static PluginsLoader pluginsLoader;
	public static ArgParser parser;
	public static String config_file = "config.json";
	public static String http_host_path = System.getProperty("user.dir") + "/host";
	public static String http_host_url = "https://x.glowman554.gq/";

	static
	{
		if (!new File(http_host_path).exists())
		{
			System.out.println("Creating http host directory");
			new File(http_host_path).mkdir();
		}
	}

	public static void main(String[] args) throws Exception
	{
		parser = new ArgParser(args);
		parser.parse();

		String token = null;
		String application_id = null;
		int port = 8888;
		boolean register_slash_commands = true;

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

		try
		{
			String _url = parser.consume_option("--download-science-v2");
			String perms = new BaseApi().request(_url + "?token=" + parser.consume_option("--token"));

			Json _json = Json.json();
			JsonNode _root = _json.parse(perms);

			FileUtils.writeFile("sciencev2.json", perms);

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
		
		try
		{
			String _url = parser.consume_option("--download-tokens");
			String tokens = new BaseApi().request(_url + "?token=" + parser.consume_option("--token"));

			Json _json = Json.json();
			JsonNode _root = _json.parse(tokens);

			FileUtils.writeFile("tokens.json", tokens);

			System.out.println("Downloaded tokens");
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
			if (System.getenv("DISCORD_TOKEN") != null)
			{
				token = System.getenv("DISCORD_TOKEN");
			}
			
			if (System.getenv("DISCORD_APP_ID") != null)
			{
				application_id = System.getenv("DISCORD_APP_ID");
			}
			
			if (System.getenv("PORT") != null)
			{
				port = Integer.parseInt(System.getenv("PORT"));
			}
			
			if (System.getenv("HTTP_HOST_PATH") != null)
			{
				http_host_path = System.getenv("HTTP_HOST_PATH");
			}

			if (System.getenv("HTTP_HOST_URL") != null)
			{
				http_host_url = System.getenv("HTTP_HOST_URL");
			}
		}
		else
		{

			config_file = parser.consume_option("--config", "config.json");
			String config = FileUtils.readFile(config_file);

			Json config_json = Json.json();
			JsonNode config_root = config_json.parse(config);

			token = config_root.get("token").asString();
			application_id = config_root.get("application_id").asString();
			if (config_root.get("register_slash_commands") != null)
			{
				register_slash_commands = config_root.get("register_slash_commands").asBoolean();
			}

			if (config_root.get("http_host_path") != null)
			{
				http_host_path = config_root.get("http_host_path").asString();
			}

			if (config_root.get("http_host_url") != null)
			{
				http_host_url = config_root.get("http_host_url").asString();
			}
		}
		
		if (System.getenv("NO_SLASH") != null)
		{
			register_slash_commands = false;
		}
		
		if (parser.is_option("--load-host"))
		{
			try
			{
				String nudel_token;
				
				if (!parser.is_option("--token"))
				{
					Scanner input = new Scanner(System.in);
					
					System.out.print("Token to acces betternudel: ");
					nudel_token = input.nextLine();
	
					input.close();
				}
				else
				{
					nudel_token = parser.consume_option("--token");
				}
				
				System.out.println("Using token " + nudel_token);
				
				String result = new BaseApi().request(http_host_url + "api/check-token?token=" + nudel_token);
				
				if (!result.equals("OK"))
				{
					System.err.println("You provided a invalid token!");
					System.exit(-1);
				}
				else
				{
					System.out.println("Token check ok!");
				}
				
				String uploaded_files = new BaseApi().request(http_host_url + "api/upload?token=" + nudel_token);
				
				if (!new File(http_host_path + "/files").isDirectory())
				{
					System.out.println("Creating directory " + http_host_path + "/files");
					new File(http_host_path + "/files").mkdir();
				}
				
				Json _json = Json.json();
				
				JsonNode root_node = _json.parse(uploaded_files);
				
				for (JsonNode node : root_node.asList())
				{
					String download_path = http_host_path + "/files/" + node.get("file_id").asString();
					String metadata_path = download_path + "!!hidden!!.json";
					
					System.out.println("Loading " + download_path + "...");
					
					FileUtils.writeFile(metadata_path, _json.serialize(node));
					new BaseApi().download(download_path, node.get("download_url").asString());
				}
				
				System.exit(0);
			}
			catch (IOException|JsonSyntaxException|IllegalArgumentException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
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
		HttpApiBaseHandler api_brainshop_path = new HttpApiBaseHandler(new ApiBrainshopHandler(), http_api, "/api/brainshop");
		HttpApiBaseHandler api_uptime_path = new HttpApiBaseHandler(new ApiUptimeHandler(), http_api, "/api/uptime");
		HttpApiBaseHandler api_load_plugin_path = new HttpApiBaseHandler(new ApiLoadPluginHandler(), http_api, "/api/load-plugin");
		HttpApiBaseHandler api_upload_path = new HttpApiBaseHandler(new ApiUploadHandler(), http_api, "/api/upload");
		HttpApiBaseHandler api_commands_path = new HttpApiBaseHandler(new ApiCommandsHandler(), http_api, "/api/commands");
		HttpApiBaseHandler api_endpoints_path = new HttpApiBaseHandler(new ApiEndpointsHandler(), http_api, "/api/endpoints");
		HttpApiBaseHandler api_check_token_path = new HttpApiBaseHandler(new ApiCheckTokenHandler(), http_api, "/api/check-token");
		HttpApiBaseHandler sync_send_handler = new HttpApiBaseHandler(new SyncSendHandler(), http_api, "/api/sync/sync-send");
		HttpApiBaseHandler sync_recv_handler = new HttpApiBaseHandler(new SyncRecvHandler(), http_api, "/api/sync/sync-recv");
		HttpApiBaseHandler api_ipinfo_path = new HttpApiBaseHandler(new ApiIpinfoHandler(), http_api, "/api/ipinfo");
		HttpApiBaseHandler api_collect_v2_path = new HttpApiBaseHandler(new ApiCollectV2Handler("sciencev2.json"), http_api, "/api/collect/v2");
		HttpApiBaseHandler api_science_v2_path = new HttpApiBaseHandler(new ApiScienceV2Handler(), http_api, "/api/science/v2");
		HttpApiBaseHandler api_tokens_path = new HttpApiBaseHandler(new ApiTokensHandler(), http_api, "/api/tokens");

		authManager = new AuthManager(http_api);

		HttpApiBaseHandler api_self_message_path = new HttpApiBaseHandler(new ApiSelfMessageHandler(authManager), http_api, "/api/self_message");

		if (!parser.is_option("--no_botnet"))
		{
			Exs exs = new Exs(http_api);
		}

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
		Discord.discord.commandManager.addCommand("chatbot", new ChatBotCommand());
		Discord.discord.commandManager.addCommand("load-plugin", new LoadPluginCommand());
		Discord.discord.commandManager.addCommand("upload", new UploadCommand());
		// Discord.discord.commandManager.addCommand("translate", new TranslateCommand());

		if (register_slash_commands)
		{
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
			Discord.discord.commandManager.addSlashCommand("chatbot", new ChatBotCommand());
			Discord.discord.commandManager.addSlashCommand("load-plugin", new LoadPluginCommand());
			// Discord.discord.commandManager.addSlashCommand("translate", new TranslateCommand());
		}

		pluginsLoader = new PluginsLoader("plugins");
		pluginsLoader.load_all();
	}
}
