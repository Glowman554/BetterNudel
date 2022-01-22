package gq.glowman554.bot;

import gq.glowman554.bot.command.CommandManager;
import gq.glowman554.bot.command.impl.*;
import gq.glowman554.bot.command.impl.testing.Testing;
import gq.glowman554.bot.config.ConfigManager;
import gq.glowman554.bot.config.impl.EnvConfigProvider;
import gq.glowman554.bot.config.impl.FileConfigProvider;
import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.platform.console.ConsolePlatform;
import gq.glowman554.bot.platform.discord.DiscordPlatform;
import gq.glowman554.bot.platform.teleram.TelegramPlatform;

public class Main {

    /*
        TODO:
            add all commands from current stable (except discord specific ones)
            add web command platform (makes http request witch return the result after on_command returns)

            port webinterface (
                - /api/v2/uptime
                - /api/v2/collect
                - /api/v2/science
                - /api/v2/commands
                - /api/v2/uploads
                - /api/v2/ipinfo
                - /api/v2/suggest
                - /api/v2/suggestions (gets suggestions)
            )

            /api/suggest (writes to file)

            -roll (with a custom range)
            -log (get the logfile)

            -compile (takes attached file compiles and runs it (sends output))
            -uptime

            plugin loading

     */

    public static CommandManager commandManager;
    public static ConfigManager configManager;

    public static void load_config() {
        Log.log("Loading config...");
        configManager = new ConfigManager();

        configManager.register(new FileConfigProvider("config.json"));
        configManager.register(new EnvConfigProvider());
    }

    public static void main(String[] args) throws Exception {
        load_config();

        try {
            commandManager = new CommandManager(configManager.get_key_as_str("prefix"));
        } catch(IllegalArgumentException e) {
            Log.log("Using default prefix '-'");
            commandManager = new CommandManager("-");
        }

        commandManager.add_command("ping", new PingCommand());
        commandManager.add_command("chatbor", new ChatBotCommand());
        commandManager.add_command("dog", new DogCommand());
        commandManager.add_command("cat", new CatCommand());
        commandManager.add_command("coinflip", new CoinFlipCommand());
        commandManager.add_command("say", new SayCommand());
        commandManager.add_command("role", new RoleCommand());
        commandManager.add_command("set_testing", new SetTestingCommand());
        commandManager.add_command("auth", new AuthCommand());
        commandManager.add_command("magic8", new Magic8Command());

        new ConsolePlatform();
        new DiscordPlatform();
        new TelegramPlatform();

        HttpApi.load();
    }
}
