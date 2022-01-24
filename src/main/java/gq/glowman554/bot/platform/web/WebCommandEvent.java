package gq.glowman554.bot.platform.web;

import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.command.CommandPlatform;
import gq.glowman554.bot.http.server.api.auth.AuthManager;
import gq.glowman554.bot.http.server.filehost.FileHostObject;
import gq.glowman554.bot.utils.ArrayUtils;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;

public class WebCommandEvent extends CommandEvent {
    private JsonNode steps = JsonNode.array();

    private final String token;

    public WebCommandEvent(String message, CommandPlatform command_platform, String token) {
        super(message, command_platform);
        this.token = token;
    }

    private JsonNode build_step(String type, String message) {
        JsonNode root = JsonNode.object();

        root.set("type", type);
        root.set("message", message);

        return root;
    }

    @Override
    public void message_send(String message) {
        steps.add(build_step("text_message", message));
    }

    @Override
    public void message_quote(String message) {
        steps.add(build_step("text_message_quote", message));
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
        return 0;
    }

    @Override
    public String get_file(int idx) {
        throw new IllegalStateException("Not supported!");
    }

    @Override
    public void message_delete() {
        steps.add(build_step("message_delete", null));
    }

    @Override
    public void send_picture(File file) {
        try {
            FileHostObject fileHostObject = FileHostObject.new_object(file, "<system>", true);

            steps.add(build_step("picture_send", fileHostObject.getFile_id()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_audio(File file) {
        try {
            FileHostObject fileHostObject = FileHostObject.new_object(file, "<system>", true);

            steps.add(build_step("audio_send", fileHostObject.getFile_id()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_video(File file) {
        try {
            FileHostObject fileHostObject = FileHostObject.new_object(file, "<system>", true);

            steps.add(build_step("video_send", fileHostObject.getFile_id()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_file(File file) {
        try {
            FileHostObject fileHostObject = FileHostObject.new_object(file, "<system>", true);

            steps.add(build_step("file_send", fileHostObject.getFile_id()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public String get_sender_id() {
        return AuthManager.instance.get_user_by_token(token);
    }

    @Override
    public String get_chat_name() {
        return AuthManager.instance.get_user_by_token(token);
    }

    public JsonNode getSteps() {
        return steps;
    }
}
