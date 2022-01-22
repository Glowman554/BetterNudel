package gq.glowman554.bot.platform.discord;

import gq.glowman554.bot.command.CommandPlatform;

public class DiscordCommandPlatform implements CommandPlatform {
    @Override
    public String format_bold(String source) {
        return String.format("**%s**", source);
    }

    @Override
    public String format_italic(String source) {
        return String.format("*%s*", source);
    }

    @Override
    public String format_code(String source) {
        return String.format("`%s`", source);
    }

    @Override
    public String format_code_block(String source) {
        return String.format("```\n%s\n```", source);
    }
}
