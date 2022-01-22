package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.ArrayUtils;

public class TestMentionIdsCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return Testing.default_config;
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        event.message_send(ArrayUtils.stringify(event.get_mention_ids(), ", "));
    }

    @Override
    public void on_register() {

    }
}
