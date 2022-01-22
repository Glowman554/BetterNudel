package gq.glowman554.bot.platform.discord;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.platform.console.ConsoleCommandEvent;
import gq.glowman554.bot.platform.console.ConsoleCommandPlatform;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DiscordReceiver extends ListenerAdapter {
    public boolean tiny_crash_report = false;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if (event.getAuthor().isBot()) {
            return;
        }

        DiscordCommandEvent commandEvent = new DiscordCommandEvent(event.getMessage().getContentRaw(), new DiscordCommandPlatform(), event);

        MessageEvent _event = new MessageEvent(commandEvent);
        _event.call();

        if (_event.isCanceled()) {
            return;
        }

        new Thread(() -> {
            try {
                Main.commandManager.on_command(commandEvent);
            } catch (Exception e) {
                e.printStackTrace();

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                String stacktrace = sw.toString();

                if (tiny_crash_report) {
                    commandEvent.message_send(commandEvent.command_platform.format_code_block(e.getClass().getSimpleName() + ": " + e.getMessage()));
                } else {
                    if (stacktrace.length() > 2000) {
                        int numChunks = (int) Math.ceil(stacktrace.length() / 2000.0);

                        for (int i = 0, o = 0; i < numChunks; i++) {
                            int x = stacktrace.length() < o + 2000 ? stacktrace.length() - o - 1 : 2000;

                            while (stacktrace.charAt(o + x) != '\n') {
                                x--;
                            }

                            event.getChannel().sendMessage("```" + stacktrace.substring(o, o + x) + "```").queue();

                            o += x;
                        }
                    } else {
                        commandEvent.message_send(commandEvent.command_platform.format_code_block(sw.toString()));
                    }
                }
            }
        }).start();
    }
}
