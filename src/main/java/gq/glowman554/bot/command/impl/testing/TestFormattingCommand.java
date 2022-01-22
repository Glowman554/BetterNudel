package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

public class TestFormattingCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return Testing.default_config;
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        event.message_send(event.command_platform.format_bold("bold"));
        event.message_send(event.command_platform.format_italic("italic"));
        event.message_send(event.command_platform.format_code("code"));
        event.message_send(event.command_platform.format_code_block("code block"));
    }

    @Override
    public void on_register() {

    }
}
