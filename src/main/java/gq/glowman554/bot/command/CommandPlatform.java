package gq.glowman554.bot.command;

public interface CommandPlatform {
    String format_bold(String source);
    String format_italic(String source);
    String format_code(String source);
    String format_code_block(String source);
}
