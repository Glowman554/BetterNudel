package gq.glowman554.bot.utils.compiler;

import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;
import gq.glowman554.bot.utils.compiler.impl.BashCompiler;
import gq.glowman554.bot.utils.compiler.impl.GccCompiler;
import gq.glowman554.bot.utils.compiler.impl.GppCompiler;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.util.HashMap;

public class CompilerManager {
    private static CompilerManager instance = null;

    private HashMap<String, CompilerInterface> compilers = new HashMap<>();

    public CompilerManager() {
        Log.log("Loading compiler manager...");

        register(new GccCompiler());
        register(new GppCompiler());
        register(new BashCompiler());
    }

    public void register(CompilerInterface compiler) {
        Log.log(String.format("Registering %s...", compiler.getClass().getSimpleName()));
        Log.log(Json.json().serialize(compiler.get_config().toJson()));

        compilers.put(compiler.get_config().file_extension, compiler);
    }

    public String compile_and_run(File file) throws Exception {
        String file_extension = FileUtils.getFileExtension(file.getAbsolutePath());

        if (!compilers.containsKey(file_extension)) {
            throw new IllegalArgumentException("Could not find compiler for file extension " + file_extension);
        }

        CompilerInterface compiler = compilers.get(file_extension);

        File compiled_file = compiler.compile_and_link(file);

        return compiler.execute(compiled_file);
    }

    public JsonNode toJson() {
        JsonNode root = JsonNode.array();

        compilers.forEach((key, value) -> {
            root.add(value.get_config().toJson());
        });

        return root;
    }

    public static CompilerManager getInstance() {
        if (instance == null) {
            instance = new CompilerManager();
        }

        return instance;
    }
}
