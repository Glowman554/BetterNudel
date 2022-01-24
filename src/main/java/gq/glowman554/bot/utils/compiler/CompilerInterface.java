package gq.glowman554.bot.utils.compiler;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.utils.ExecutionEngine;

import java.io.File;
import java.io.IOException;

public abstract class CompilerInterface {
    public abstract CompilerConfig get_config();

    // return compiled file!
    public abstract File compile_and_link(File file) throws Exception;
    public abstract String execute(File compiled_file) throws Exception;

    protected boolean allow_unsafe() {
        boolean allow_unsafe = false;
        try {
            allow_unsafe = Main.configManager.get_key_as_str("allow_unsafe_exec").equalsIgnoreCase("true");
        } catch (IllegalArgumentException ignored) {

        }

        return allow_unsafe;
    }
}
