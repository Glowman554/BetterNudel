package gq.glowman554.bot.utils.compiler.impl;

import gq.glowman554.bot.utils.ExecutionEngine;
import gq.glowman554.bot.utils.compiler.CompilerConfig;
import gq.glowman554.bot.utils.compiler.CompilerInterface;

import java.io.File;

public class BashCompiler extends CompilerInterface {
    @Override
    public CompilerConfig get_config() {
        return new CompilerConfig("sh", "bash");
    }

    @Override
    public File compile_and_link(File file) throws Exception {
        return file;
    }

    @Override
    public String execute(File compiled_file) throws Exception {
        return new ExecutionEngine(allow_unsafe()).execute(String.format("%s %s", get_config().compiler_name, compiled_file.getAbsolutePath()));
    }
}
