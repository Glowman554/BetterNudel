package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.ArrayUtils;

public class RepeatCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Repeat a command!", String.format("Use '%srepeat [count][command]' to repeat a command!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length < 2) {
            event.message_send("Not enough arguments");
        } else {
            int count = Integer.parseInt(event.get_arguments()[0]);

            String[] args = CommandEvent.get_arguments(event.get_arguments());

            if (!args[0].startsWith(Main.commandManager.prefix)) {
                args[0] = Main.commandManager.prefix + args[0];
            }

            if (args[0].equals(Main.commandManager.prefix + "repeat")) {
                event.message_send("You can't repeat a repeat command");
            } else {
                if (count < 11 || Main.commandManager.permissionManager.has_permission(event.get_sender_id(), "no_limit")) {

                    if (count < 0) {
                        event.message_send("Count must be greater than 0");
                    } else {
                        event._message = ArrayUtils.stringify(args, " ");

                        for (int i = 0; i < count; i++) {
                            Main.commandManager.on_command(event);
                        }
                    }
                } else {
                    event.message_send("Count must be less than 10");
                }
            }
        }
    }

    @Override
    public void on_register() {

    }
}
