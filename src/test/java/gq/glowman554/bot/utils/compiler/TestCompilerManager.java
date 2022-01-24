package gq.glowman554.bot.utils.compiler;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.FileUtils;
import net.shadew.json.Json;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCompilerManager {
    @Test
    public void test1() throws Exception {
        Main.load_config();

        CompilerManager compilerManager = new CompilerManager();

        FileUtils.writeFile("tmp/test.c", "int main() { printf(\"Hello World\\n\"); return 0; }");

        assertEquals("Hello World\n", compilerManager.compile_and_run(new File("tmp/test.c")));
    }

    @Test
    public void test2() throws Exception {
        Main.load_config();

        CompilerManager compilerManager = new CompilerManager();

        FileUtils.writeFile("tmp/test.cpp", "#include <iostream>\nint main() { std::cout << \"Hello World\\n\"; return 0; }");

        assertEquals("Hello World\n", compilerManager.compile_and_run(new File("tmp/test.cpp")));
    }

    @Test
    public void test3() throws Exception {
        Main.load_config();

        CompilerManager compilerManager = new CompilerManager();

        FileUtils.writeFile("tmp/test.sh", "echo Hello World");

        assertEquals("Hello World\n", compilerManager.compile_and_run(new File("tmp/test.sh")));
    }

    @Test
    public void test4() {
        Main.load_config();

        CompilerManager compilerManager = new CompilerManager();

        Log.log(Json.json().serialize(compilerManager.toJson()));
    }
}
