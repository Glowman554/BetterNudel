package gq.glowman554.bot;

import gq.glowman554.bot.command.CommandManager;
import gq.glowman554.bot.command.impl.*;
import gq.glowman554.bot.config.ConfigManager;
import gq.glowman554.bot.config.impl.EnvConfigProvider;
import gq.glowman554.bot.config.impl.FileConfigProvider;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.platform.console.ConsolePlatform;
import gq.glowman554.bot.platform.discord.DiscordPlatform;
import gq.glowman554.bot.platform.teleram.TelegramPlatform;
import gq.glowman554.bot.plugin.PluginLoader;

public class Main {

    /*
        TODO:
            add web command platform (makes http request witch return the result after on_command returns)

            port webinterface (
                - /api/v2/collect
                - /api/v2/science
                - /api/v2/commands
                - /api/v2/uploads
                - /api/v2/ipinfo
                - /api/v2/suggest
                - /api/v2/suggestions (gets suggestions)
            )

            /api/suggest (writes to file (legacy package))

            -roll (with a custom range)

            -compile (takes attached file compiles and runs it (sends output))

     */

    public static CommandManager commandManager;
    public static ConfigManager configManager;
    public static PluginLoader pluginLoader;
    public static long startTime;

    public static void load_config() {
        Log.log("Loading config...");
        configManager = new ConfigManager();

        configManager.register(new FileConfigProvider("config.json"));
        configManager.register(new EnvConfigProvider());
    }

    public static void main(String[] args) throws Exception {
        startTime = System.currentTimeMillis();

        load_config();

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

        new ConsolePlatform();
        new DiscordPlatform();
        new TelegramPlatform();

        HttpApi.load();

        pluginLoader.load();
    }
}
