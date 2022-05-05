package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.externapi.ChatBotApi;
import gq.glowman554.bot.utils.ArrayUtils;

import java.io.IOException;

public class ChatBotCommand implements Command {
    private ChatBotApi api = new ChatBotApi();

    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Chat with a chatbot", String.format("Use '%schatbot [input]' to chat with the chatbot!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("Invalid arguments");
        } else {
            event.message_send(api.response(ArrayUtils.stringify(event.get_arguments(), " "), event.get_chat_name()));
        }
    }

    @Override
    public void on_register() {
        EventManager.register(this);
    }

    @EventTarget
    public void onMessage(MessageEvent event) {
        if (event.commandEvent.get_chat_name().contains("chatbot")) {
            try {
                event.commandEvent.message_send(api.response(event.commandEvent.get_message(), event.commandEvent.get_chat_name()));
                event.setCanceled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
