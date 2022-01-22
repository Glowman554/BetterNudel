package gq.glowman554.bot.platform.console;

import gq.glowman554.bot.command.CommandPlatform;

public class ConsoleCommandPlatform implements CommandPlatform {
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
}
