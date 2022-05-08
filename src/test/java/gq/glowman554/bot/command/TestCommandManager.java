package gq.glowman554.bot.command;

import gq.glowman554.bot.log.Log;
import net.shadew.json.Json;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestCommandManager {
    /*
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("help short", "help long", null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        Log.log("execute()");
    }

    @Override
    public void on_register() {
        Log.log("on_register()");
    }

    @Test
    public void test1() throws Exception {
        CommandManager commandManager = new CommandManager("-");
        commandManager.add_command("test", new TestCommandManager());

        CommandPlatform commandPlatform = new CommandPlatform() {
            @Override
            public String format_bold(String source) {
                return source;
            }

            @Override
            public String format_italic(String source) {
                return source;
            }

            @Override
            public String format_code(String source) {
                return source;
            }

            @Override
            public String format_code_block(String source) {
                return source;
            }
        };

        commandManager.on_command(new CommandEvent("-help", commandPlatform) {
            @Override
            public void message_send(String message) {
                Log.log(message);
            }

            @Override
            public void message_quote(String message) {

            }

            @Override
            public String[] get_mention_ids() {
                return new String[0];
            }

            @Override
            public int get_num_files() {
                return 0;
            }

            @Override
            public String get_file(int idx) {
                return null;
            }

            @Override
            public void message_delete() {

            }

            @Override
            public void send_picture(File file) {

            }

            @Override
            public void send_audio(File file) {

            }

            @Override
            public void send_video(File file) {

            }

            @Override
            public void send_file(File file) {

            }

            @Override
            public String get_sender_id() {
                return null;
            }

            @Override
            public String get_chat_name() {
                return null;
            }
        });

        Log.log(Json.json().serialize(commandManager.toJson()));
    }

     */
}
