package gq.glowman554.bot;

import gq.glowman554.bot.command.CommandManager;
import gq.glowman554.bot.command.impl.*;
import gq.glowman554.bot.config.ConfigManager;
import gq.glowman554.bot.config.impl.EnvConfigProvider;
import gq.glowman554.bot.config.impl.FileConfigProvider;
import gq.glowman554.bot.config.impl.RedisConfigProvider;
import gq.glowman554.bot.externapi.SpotifyApi;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.platform.console.ConsolePlatform;
import gq.glowman554.bot.platform.discord.DiscordPlatform;
import gq.glowman554.bot.platform.teleram.TelegramPlatform;
import gq.glowman554.bot.platform.web.WebPlatform;
import gq.glowman554.bot.plugin.PluginLoader;
import gq.glowman554.bot.utils.MultiThreadHelper;

public class Main {
    public static CommandManager commandManager;
    public static ConfigManager configManager;
    public static PluginLoader pluginLoader;
    public static long startTime;
    public static boolean tiny_crash_report = false;

    public static DiscordPlatform discordPlatform;
    public static TelegramPlatform telegramPlatform;
    public static ConsolePlatform consolePlatform;
    public static WebPlatform webPlatform;

    public static void load_config() {
        Log.log("Loading config...");
        configManager = new ConfigManager();

        configManager.register(new RedisConfigProvider());
        configManager.register(new FileConfigProvider("config.json"));
        configManager.register(new EnvConfigProvider());
    }

    public static void load_config(String config_file) {
        Log.log("--- WARNING --- Loading custom config " + config_file + "...");
        configManager = new ConfigManager();

        configManager.register(new RedisConfigProvider());
        configManager.register(new FileConfigProvider(config_file));
    }


    public static void main(String[] args) throws Exception {
        startTime = System.currentTimeMillis();

        load_config();
        configManager.sync();

        try {
            commandManager = new CommandManager(configManager.get_key_as_str("prefix"));
        } catch (IllegalArgumentException e) {
            Log.log("Using default prefix '-'");
            commandManager = new CommandManager("-");
        }

        pluginLoader = new PluginLoader("plugins");

        commandManager.add_command("ping", new PingCommand());
        commandManager.add_command("chatbot", new ChatBotCommand());
        commandManager.add_command("dog", new DogCommand());
        commandManager.add_command("cat", new CatCommand());
        commandManager.add_command("coinflip", new CoinFlipCommand());
        commandManager.add_command("say", new SayCommand());
        commandManager.add_command("role", new RoleCommand());
        commandManager.add_command("set_testing", new SetTestingCommand());
        commandManager.add_command("auth", new AuthCommand());
        commandManager.add_command("magic8", new Magic8Command());
        commandManager.add_command("commit", new CommitCommand());
        commandManager.add_command("corona", new CoronaCommand());
        commandManager.add_command("exec", new ExecCommand());
        commandManager.add_command("fact", new FactCommand());
        commandManager.add_command("fox", new FoxCommand());
        commandManager.add_command("furry", new FurryCommand());
        commandManager.add_command("im_18", new Im18Command());
        commandManager.add_command("joke", new JokeCommand());
        commandManager.add_command("meme", new MemeCommand());
        commandManager.add_command("tts", new TtsCommand());
        commandManager.add_command("wikipedia", new WikipediaCommand());
        commandManager.add_command("yiff", new YiffCommand());
        commandManager.add_command("repeat", new RepeatCommand());
        commandManager.add_command("upload", new UploadCommand());
        commandManager.add_command("log", new LogCommand());
        commandManager.add_command("uptime", new UptimeCommand());
        commandManager.add_command("roll", new RollCommand());
        commandManager.add_command("compile", new CompileCommand());
        commandManager.add_command("calc", new CalcCommand());
        commandManager.add_command("token", new TokenCommand());
        commandManager.add_command("spotify", new SpotifyCommand());

        HttpApi.load();

        var consolePlatformWaiter = MultiThreadHelper.run(ConsolePlatform.class);
        var discordPlatformWaiter = MultiThreadHelper.run(DiscordPlatform.class);
        var telegramPlatformWaiter = MultiThreadHelper.run(TelegramPlatform.class);
        var webPlatformWaiter = MultiThreadHelper.run(WebPlatform.class);

        consolePlatform = (ConsolePlatform) consolePlatformWaiter.complete().instance;
        discordPlatform = (DiscordPlatform) discordPlatformWaiter.complete().instance;
        telegramPlatform = (TelegramPlatform) telegramPlatformWaiter.complete().instance;
        webPlatform = (WebPlatform) webPlatformWaiter.complete().instance;

        pluginLoader.load();

        Log.log("Startup complete!");
    }
}
