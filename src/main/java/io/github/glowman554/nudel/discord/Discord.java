package io.github.glowman554.nudel.discord;

import javax.security.auth.login.LoginException;

import io.github.glowman554.nudel.discord.commands.CommandManager;
import io.github.glowman554.nudel.discord.MessageLogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Discord
{
	// ------------------- static fields -------------------
	public static Discord discord;
	
	public static void init(String token, String application_id) throws LoginException, InterruptedException
	{
		Discord.discord = new Discord(token, application_id);
	}
	// -----------------------------------------------------

	String current_rp;

	public String token;
	public String application_id;
	public JDA jda;
	public CommandManager commandManager;

	public DiscordReceiver receiver;

	private Discord(String token, String application_id) throws LoginException, InterruptedException
	{
		this.token = token;
		this.application_id = application_id;

		JDABuilder jdaBuilder = JDABuilder.createDefault(this.token);
        jda = jdaBuilder.build();

		jda.awaitReady();

		receiver = new DiscordReceiver();

		jda.addEventListener(receiver);
		jda.addEventListener(new MessageLogger());

		commandManager = new CommandManager("-");

		this.setDefaultRP();

		new Thread() {
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000 * 60);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						return;
					}

					Discord.discord.updateRP();
				}
			}
		}.start();
	}

	public void setDefaultRP()
	{
		this.current_rp = this.commandManager.prefix + "help";
		jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.current_rp, "https://www.twitch.tv/glowman434"));
	}

	public void setRP(String rp)
	{
		this.current_rp = rp;
		jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.current_rp, "https://www.twitch.tv/glowman434"));
	}

	public void updateRP()
	{
		jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.current_rp, "https://www.twitch.tv/glowman434"));
	}
}
