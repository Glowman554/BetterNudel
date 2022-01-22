package gq.glowman554.bot.event.impl;

import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.event.EventCancelable;

public class MessageEvent extends EventCancelable {
    // NOTE: get_command and get_arguments will be wrong here
    public final CommandEvent commandEvent;

    public MessageEvent(CommandEvent commandEvent) {
        this.commandEvent = commandEvent;
    }
}
