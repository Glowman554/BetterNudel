package io.github.glowman554.nudel.discord.commands.impl;

import java.io.IOException;

import io.github.glowman554.nudel.api.ChatBotApi;
import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;
import io.github.glowman554.nudel.discord.commands.SlashCommandRegister;
import io.github.glowman554.nudel.utils.ArrayUtils;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.shadew.json.JsonSyntaxException;

public class ChatBotCommand extends ListenerAdapter implements Command, SlashCommand
{

	private ChatBotApi api = new ChatBotApi();

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length == 0)
		{
			event.commandFail("Invalid arguments");
		}
		else
		{
			event.commandSuccess(api.response(ArrayUtils.stringify(event.args, " "), event.event.getChannel().getId()));
		}
	}

	@Override
	public void on_register()
	{
		Discord.discord.jda.addEventListener(this);
	}

	@Override
	public String get_short_help()
	{
		return "Chat with a chatbot";
	}

	@Override
	public String get_long_help()
	{
		return String.format("Use '%schatbot [input]' to chat with the chatbot!", Discord.discord.commandManager.prefix);
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public void execute(SlashCommandEvent event) throws Exception
	{
		event.reply(api.response(event.getOption("input").getAsString(), event.getChannel().getId())).queue();
	}

	@Override
	public void on_slash_register()
	{
		SlashCommandRegister reg = new SlashCommandRegister("chatbot", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
			new SlashCommandParameter("input", "Input for chatbot", SlashCommandParameter.STRING, true)
		});

		try
		{
			reg.doRegister(Discord.discord.token, Discord.discord.application_id);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		super.onMessageReceived(event);

		if(event.getAuthor().isBot()) {
			return;
		}

		if(event.getTextChannel().getName().contains("chatbot")) {
			try
			{
				event.getTextChannel().sendMessage(api.response(event.getMessage().getContentRaw(), event.getTextChannel().getId())).queue();
			}
			catch (IOException | JsonSyntaxException e)
			{
				e.printStackTrace();
			}
		}
    }
}
