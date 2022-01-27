package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.math.MathInterpreter;

public class CalcCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Calculate something!", String.format("Use '%scalc [expr]' to calculate something!", Main.commandManager.prefix), null);
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length == 0) {
            event.message_send("Expected at leas t 1 argument!");
        } else {
            String expr = ArrayUtils.stringify(event.get_arguments(), " ");

            event.message_send("The result is: " + MathInterpreter.eval(expr));
        }
    }

    @Override
    public void on_register() {

    }
}
