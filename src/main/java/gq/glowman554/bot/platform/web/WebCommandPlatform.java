package gq.glowman554.bot.platform.web;

import gq.glowman554.bot.command.CommandPlatform;

public class WebCommandPlatform implements CommandPlatform {
    @Override
    public String format_bold(String source) {
        return String.format("<b>%s</b>", source);
    }

    @Override
    public String format_italic(String source) {
        return String.format("<i>%s</i>", source);
    }

    @Override
    public String format_code(String source) {
        return String.format("<code>%s</code>", source);
    }

    @Override
    public String format_code_block(String source) {
        return String.format("<code>\n%s\n</code>", source);
    }
}
