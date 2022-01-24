package gq.glowman554.bot.command.impl;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.utils.compiler.CompilerManager;

import java.io.File;

public class CompileCommand implements Command {
    @Override
    public CommandConfig get_config() {
        return new CommandConfig("Compile and run a file!", String.format("Use '%scompile' to compile the attached file and run it!", Main.commandManager.prefix), "compile");
    }

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.get_arguments().length != 0 || event.get_num_files() == 0) {
            event.message_send("Too many arguments or missing file!");
        } else {
            File file = new File(event.get_file(0));

            event.message_send(event.command_platform.format_code_block(CompilerManager.getInstance().compile_and_run(file)));
        }
    }

    @Override
    public void on_register() {

    }
}
