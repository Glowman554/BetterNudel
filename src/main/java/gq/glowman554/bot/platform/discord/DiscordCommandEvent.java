package gq.glowman554.bot.platform.discord;

import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.command.CommandPlatform;
import gq.glowman554.bot.utils.FileUtils;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;

public class DiscordCommandEvent extends CommandEvent {
    private final MessageReceivedEvent event;

    public DiscordCommandEvent(String message, CommandPlatform command_platform, MessageReceivedEvent event) {
        super(message, command_platform);
        this.event = event;
    }

    @Override
    public void message_send(String message) {
        event.getChannel().sendMessage(message).queue();
    }

    @Override
    public void message_quote(String message) {
        event.getMessage().reply(message).queue();
    }

    @Override
    public String[] get_mention_ids() {
        ArrayList<String> mentions = new ArrayList<>();

        for (IMentionable mentionable : event.getMessage().getMentions()) {
            mentions.add(mentionable.getId());
        }

        return mentions.toArray(new String[0]);
    }

    @Override
    public int get_num_files() {
        return event.getMessage().getAttachments().size();
    }

    @Override
    public String get_file(int idx) {
        Message.Attachment attachment = event.getMessage().getAttachments().get(idx);

        String path = FileUtils.randomTmpFile(attachment.getFileExtension());

        attachment.downloadToFile(path).join();

        return path;
    }

    @Override
    public void message_delete() {
        event.getMessage().delete().queue();
    }

    @Override
    public void send_picture(File file) {
        event.getChannel().sendFile(file).queue();
    }

    @Override
    public void send_audio(File file) {
        event.getChannel().sendFile(file).queue();
    }

    @Override
    public void send_video(File file) {
        event.getChannel().sendFile(file).queue();
    }

    @Override
    public void send_file(File file) {
        event.getChannel().sendFile(file).queue();
    }

    @Override
    public String get_sender_id() {
        return event.getAuthor().getId();
    }
}
