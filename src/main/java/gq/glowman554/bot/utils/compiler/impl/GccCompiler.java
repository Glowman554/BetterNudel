package gq.glowman554.bot.utils.compiler.impl;

import gq.glowman554.bot.utils.ExecutionEngine;
import gq.glowman554.bot.utils.compiler.CompilerConfig;
import gq.glowman554.bot.utils.compiler.CompilerInterface;

import java.io.File;

public class GccCompiler extends CompilerInterface {
    @Override
    public CompilerConfig get_config() {
        return new CompilerConfig("c", "gcc");
    }

    @Override
    public File compile_and_link(File file) throws Exception {
        ExecutionEngine executionEngine = new ExecutionEngine(false);

        String command1 = String.format("%s -o %s.o -c %s", get_config().compiler_name, file.getAbsolutePath(), file.getAbsolutePath());
        executionEngine.execute(command1);

        String command2 = String.format("%s -o %s.elf %s.o", get_config().compiler_name, file.getAbsolutePath(), file.getAbsolutePath());
        executionEngine.execute(command2);

        return new File(file.getAbsolutePath() + ".elf");
    }

    @Override
    public String execute(File compiled_file) throws Exception {
        return new ExecutionEngine(allow_safe_exec()).execute(compiled_file.getAbsolutePath());
    }
}
