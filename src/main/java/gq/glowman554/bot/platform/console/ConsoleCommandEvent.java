package gq.glowman554.bot.platform.console;

import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.command.CommandPlatform;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;

import java.io.File;

public class ConsoleCommandEvent extends CommandEvent {
    private boolean want_file = false;
    private boolean asked_want_file = false;

    public ConsoleCommandEvent(String message, CommandPlatform command_platform) {
        super(message, command_platform);
    }

    @Override
    public void message_send(String message) {
        Log.log(message);
    }

    @Override
    public void message_quote(String message) {
        Log.log(message);
    }

    @Override
    public String[] get_mention_ids() {
        String[] mentions = new String[0];

        for (String message_part : _message.split(" ")) {
            if (message_part.startsWith("m@")) {
                mentions = ArrayUtils.add(mentions, message_part.substring(2));
            }
        }

        return mentions;
    }

    @Override
    public int get_num_files() {
        if (!asked_want_file) {
            want_file = new FileChoseUi().want_chose();
            asked_want_file = true;
        }

        return want_file ? 1 : 0;
    }

    @Override
    public String get_file(int idx) {
        return new FileChoseUi().chose();
    }

    @Override
    public void message_delete() {

    }

    @Override
    public void send_picture(File file) {
        Log.log("File: " + file.getAbsolutePath());
    }

    @Override
    public void send_audio(File file) {
        Log.log("File: " + file.getAbsolutePath());
    }

    @Override
    public void send_video(File file) {
        Log.log("File: " + file.getAbsolutePath());
    }

    @Override
    public void send_file(File file) {
        Log.log("File: " + file.getAbsolutePath());
    }

    @Override
    public String get_sender_id() {
        return "<stdin>";
    }

    @Override
    public String get_chat_name() {
        return "<stdin>";
    }
}
