package io.github.glowman554.nudel.discord;

import java.io.PrintWriter;
import java.io.StringWriter;

import io.github.glowman554.nudel.discord.commands.CommandEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordReceiver extends ListenerAdapter
{
	public boolean tiny_crash_report = false;
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		super.onMessageReceived(event);

		if (event.getAuthor().isBot())
		{
			return;
		}

		CommandEvent commandEvent = new CommandEvent(event.getMessage().getContentRaw(), event.getMessage().getContentRaw().split(" ")[0], CommandEvent.getArguments(event.getMessage().getContentRaw().split(" ")), event);

		new Thread()
		{
			public void run()
			{
				try
				{
					Discord.discord.commandManager.onCommand(commandEvent);
				}
				catch (Exception e)
				{
					e.printStackTrace();
		
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
		
					String stacktrace = sw.toString();
		
					if (tiny_crash_report)
					{
						commandEvent.commandFail(e.getClass().getSimpleName() + ": " + e.getMessage());
					}
					else
					{
						if (stacktrace.length() > 2000)
						{
							int numChunks = (int) Math.ceil(stacktrace.length() / 2000.0);
		
							for (int i = 0, o = 0; i < numChunks; i++)
							{
								int x = stacktrace.length() < o + 2000 ? stacktrace.length() - o - 1 : 2000;
		
								while (stacktrace.charAt(o + x) != '\n')
								{
									x--;
								}
		
								event.getChannel().sendMessage("```" + stacktrace.substring(o, o + x) + "```").queue();
		
								o += x;
							}
						}
						else
						{
							commandEvent.commandFail(sw.toString());
						}
					}
				}
			};
		}.start();
	}

	@Override
	public void onSlashCommand(SlashCommandEvent event)
	{
		super.onSlashCommand(event);

		new Thread()
		{
			public void run()
			{
				try
				{
					Discord.discord.commandManager.onSlashCommand(event);
				}
				catch (Exception e)
				{
					e.printStackTrace();
		
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
		
					String stacktrace = sw.toString();

					if (!event.isAcknowledged())
					{
						event.reply("Oops something went wrong!\n").queue();
					}
		
					if (tiny_crash_report)
					{
						event.getChannel().sendMessage(e.getClass().getSimpleName() + ": " + e.getMessage()).queue();
					}
					else
					{
						if (stacktrace.length() > 2000)
						{
							int numChunks = (int) Math.ceil(stacktrace.length() / 2000.0);
		
							for (int i = 0, o = 0; i < numChunks; i++)
							{
								int x = stacktrace.length() < o + 2000 ? stacktrace.length() - o - 1 : 2000;
		
								while (stacktrace.charAt(o + x) != '\n')
								{
									x--;
								}
		
								event.getChannel().sendMessage("```" + stacktrace.substring(o, o + x) + "```").queue();
								
								o += x;
							}
						}
						else
						{
							event.getChannel().sendMessage("```" + stacktrace + "```").queue();
						}
					}
				}
			}
		}.start();
	}
}
