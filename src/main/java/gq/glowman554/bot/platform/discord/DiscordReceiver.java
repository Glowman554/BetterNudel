package gq.glowman554.bot.platform.discord;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.utils.MultiThreadHelper;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordReceiver extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
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

        MultiThreadHelper.run(() -> {
            try {
                Main.commandManager.on_command(commandEvent);
            } catch (Exception e) {
                commandEvent.handle_exception(e);
            }
        });
    }
}
