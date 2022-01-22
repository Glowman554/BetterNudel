package gq.glowman554.bot.platform.teleram;

import gq.glowman554.bot.command.CommandPlatform;

public class TelegramCommandPlatform implements CommandPlatform {
    public boolean reply_with_html = false;
    @Override
    public String format_bold(String source) {
        reply_with_html = true;
        return String.format("<b>%s</b>", source);
    }

    @Override
    public String format_italic(String source) {
        reply_with_html = true;
        return String.format("<i>%s</i>", source);
    }

    @Override
    public String format_code(String source) {
        reply_with_html = true;
        return String.format("<code>%s</code>", source);
    }

    @Override
    public String format_code_block(String source) {
        reply_with_html = true;
        return String.format("<code>%s</code>", source);
    }
}
