package gq.glowman554.bot.utils;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

public class TestMultiThreadHelper {
    @Test
    public void test1() {
        MultiThreadHelper.run(() -> {
            Log.log("Running in a thread!");
        }).complete();

        Log.log("After .complete()");
    }

    @Test
    public void test2() {
        TestClass testClass = (TestClass) MultiThreadHelper.run(TestClass.class).complete().instance;

        Log.log("After .complete()");
        Log.log(testClass.test);
    }

    public static class TestClass {
        public String test;

        public TestClass() {
            Log.log("Running in a thread!");
            test = "Hello World";
        }
    }
}
