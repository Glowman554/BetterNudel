package gq.glowman554.bot.utils.compiler.impl;

import gq.glowman554.bot.utils.ExecutionEngine;
import gq.glowman554.bot.utils.compiler.CompilerConfig;
import gq.glowman554.bot.utils.compiler.CompilerInterface;
import gq.glowman554.bot.utils.compiler.CompilerManager;

import java.io.File;

public class MicCompiler extends CompilerInterface {
    @Override
    public CompilerConfig get_config() {
        return new CompilerConfig("mik", "mic");
    }

    @Override
    public File compile_and_link(File file) throws Exception {
        ExecutionEngine executionEngine = new ExecutionEngine(false);

        String command1 = String.format("%s -o %s.c -i %s", get_config().compiler_name, file.getAbsolutePath(), file.getAbsolutePath());
        executionEngine.execute(command1);

        return CompilerManager.getInstance().compile_and_link(new File(file.getAbsolutePath() + ".c"));
    }

    @Override
    public String execute(File compiled_file) throws Exception {
        return new ExecutionEngine(allow_safe_exec()).execute(compiled_file.getAbsolutePath());
    }
}
