package gq.glowman554.bot.command;

import java.io.File;

public abstract class CommandEvent {
    public final String _message;
    public final CommandPlatform command_platform;

    public CommandEvent(String message, CommandPlatform command_platform) {
        _message = message;
        this.command_platform = command_platform;
    }

    private static String[] get_arguments(String[] array) {
        String[] args = new String[array.length - 1];
        System.arraycopy(array, 1, args, 0, array.length - 1);
        return args;
    }

    public abstract void message_send(String message);
    public abstract void message_quote(String message);

    public String get_message() {
        return _message;
    }

    public String get_command() {
        return _message.split(" ")[0];
    }

    public String[] get_arguments() {
        return get_arguments(_message.split(" "));
    }

    public abstract String[] get_mention_ids();

    public abstract int get_num_files();
    public abstract String get_file(int idx);

    public abstract void message_delete();

    public abstract void send_picture(File file);
    public abstract void send_audio(File file);
    public abstract void send_video(File file);
    public abstract void send_file(File file);

    @Override
    public String toString() {
        return "CommandEvent{_message='" + _message + "'}";
    }

    public abstract String get_sender_id();
    public abstract String get_chat_name();
}
