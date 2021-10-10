package io.github.glowman554.nudel.discord;

import javax.security.auth.login.LoginException;

import io.github.glowman554.nudel.discord.commands.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Discord
{
	// ------------------- static fields -------------------
	public static Discord discord;
	
	public static void init(String token) throws LoginException
	{
		Discord.discord = new Discord(token);
	}
	// -----------------------------------------------------

	private String token;
	public JDA jda;
	public CommandManager commandManager;

	public DiscordReceiver receiver;

	private Discord(String token) throws LoginException
	{
		this.token = token;

		JDABuilder jdaBuilder = JDABuilder.createDefault(this.token);
        jda = jdaBuilder.build();

		receiver = new DiscordReceiver();

		jda.addEventListener(receiver);

		commandManager = new CommandManager("-");

		this.setDefaultRP();
	}

	public void setDefaultRP()
	{
		jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.commandManager.prefix + "help", "https://www.twitch.tv/glowman434"));
	}

	public void setRP(String rp)
	{
		jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(rp, "https://www.twitch.tv/glowman434"));
	}
}
