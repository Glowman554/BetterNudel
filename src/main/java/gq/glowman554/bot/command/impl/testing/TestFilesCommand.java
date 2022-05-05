package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class TestFilesCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return Testing.default_config;
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_num_files() == 0) {
            event.message_send("No files!");
        } else {
            event.message_send(event.get_file(0));
        }
    }

    @Override
    public void on_register() {

    }
}
