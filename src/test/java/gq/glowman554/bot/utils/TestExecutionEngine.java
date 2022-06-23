package gq.glowman554.bot.utils;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExecutionEngine {
    // @Test
    // public void test1() throws ExecutionEngine.ExecutionEngineError, IOException {
    //     ExecutionEngine ee = new ExecutionEngine(false);
    //     String result = ee.execute("echo test");

    //     Log.log(result);

    //     assertEquals("test\n", result);
    // }

    /*
    @Test
    public void test2() throws ExecutionEngine.ExecutionEngineError, IOException {
        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            ExecutionEngine ee = new ExecutionEngine(true);
            String result = ee.execute("echo test");

            Log.log(result);

            assertEquals("test\n", result);
        } else {
            Log.log("Cannot test safe execution on windows!");
        }
    }

    @Test
    public void test3() throws ExecutionEngine.ExecutionEngineError, IOException {
        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            ExecutionEngine ee = new ExecutionEngine(true);
            String result = ee.execute("ls /");

            Log.log(result);
        } else {
            Log.log("Cannot test safe execution on windows!");
        }
    }
     */
}
