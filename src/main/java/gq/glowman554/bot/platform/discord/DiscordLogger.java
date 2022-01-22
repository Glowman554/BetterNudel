package gq.glowman554.bot.platform.discord;

import gq.glowman554.bot.log.Log;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordLogger extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.isFromType(ChannelType.PRIVATE)) {
            Log.log(String.format("[PM] %s: %s", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay()));
        } else {
            Log.log(String.format("[%s][%s] %s: %s", event.getGuild().getName(), event.getTextChannel().getName(), event.getAuthor().getAsTag(), event.getMessage().getContentDisplay()));
        }
    }
}
