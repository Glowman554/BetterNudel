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
            port webinterface
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
        commandManager.add_command("dog", new DogCommand());
        commandManager.add_command("cat", new CatCommand());
        commandManager.add_command("coinflip", new CoinFlipCommand());
        commandManager.add_command("say", new SayCommand());
        commandManager.add_command("role", new RoleCommand());
        commandManager.add_command("set_testing", new SetTestingCommand());
        commandManager.add_command("auth", new AuthCommand());

        new ConsolePlatform();
        new DiscordPlatform();
        new TelegramPlatform();

        HttpApi.load();
    }
}
