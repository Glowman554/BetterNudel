package io.github.glowman554.nudel;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.impl.CatCommand;
import io.github.glowman554.nudel.discord.commands.impl.CoinflipCommand;
import io.github.glowman554.nudel.discord.commands.impl.DogCommand;
import io.github.glowman554.nudel.discord.commands.impl.FoxCommand;
import io.github.glowman554.nudel.discord.commands.impl.FurryCommand;
import io.github.glowman554.nudel.discord.commands.impl.Im18Command;
import io.github.glowman554.nudel.discord.commands.impl.NickCommand;
import io.github.glowman554.nudel.discord.commands.impl.PingCommand;
import io.github.glowman554.nudel.discord.commands.impl.RepeatCommand;
import io.github.glowman554.nudel.discord.commands.impl.SayCommand;
import io.github.glowman554.nudel.discord.commands.impl.YiffCommand;
import io.github.glowman554.nudel.utils.ArgParser;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class Main {

	public static void main(String[] args) throws Exception
	{
		ArgParser parser = new ArgParser(args);
		parser.parse();

		String config_file = parser.consume_option("--config", "config.json");
		String config = FileUtils.readFile(config_file);

		Json config_json = Json.json();
		JsonNode config_root = config_json.parse(config);

		String token = config_root.get("token").asString();

		Discord.init(token);

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
	}
}
