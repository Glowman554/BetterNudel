package gq.glowman554.bot.utils.compiler;

import net.shadew.json.JsonNode;

public class CompilerConfig {
    public final String file_extension;
    public final String compiler_name;

    public CompilerConfig(String file_extension, String compiler_name) {
        this.file_extension = file_extension;
        this.compiler_name = compiler_name;
    }

    public JsonNode toJson() {
        JsonNode root = JsonNode.object();

        root.set("file_extension", file_extension);
        root.set("compiler_name", compiler_name);

        return root;
    }
}
