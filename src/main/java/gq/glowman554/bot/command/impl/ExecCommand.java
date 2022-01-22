package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.ExecutionEngine;

public class ExecCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Execute a shell command.", String.format("Use '%sexec [command]' to execute a shell command.", Main.commandManager.prefix), "exec");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("Please specify a command to execute.");
        } else {
            String command = ArrayUtils.stringify(event.get_arguments(), " ");

            boolean allow_unsafe = false;
            try {
                allow_unsafe = Main.configManager.get_key_as_str("allow_unsafe_exec").equalsIgnoreCase("true");
            } catch (IllegalArgumentException ignored) {

            }

            if (allow_unsafe) {
                Log.log("--- WARNING --- Executing command unsafe!!");
            }

            ExecutionEngine engine = new ExecutionEngine(!allow_unsafe);

            event.message_send(engine.execute(command));
        }
    }

    @Override
    public void on_register() {

    }
}
