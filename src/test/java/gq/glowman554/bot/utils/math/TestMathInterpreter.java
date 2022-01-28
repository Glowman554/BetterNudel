package gq.glowman554.bot.utils.math;

import gq.glowman554.bot.log.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMathInterpreter {
    @Test
    public void test1() {
        assertEquals(50d, MathInterpreter.eval("5 * 2 * 5", Log::log));
    }

    @Test
    public void test2() {
        assertEquals(6d, MathInterpreter.eval("(1 + 2) * 2", Log::log));
    }

    @Test
    public void test3() {
        assertEquals(7d, MathInterpreter.eval("1 + 2 * 3", Log::log));
    }

    @Test
    public void test4() {
        // floating point errors i hate you!
        assertEquals(0.6000000000000001d, MathInterpreter.eval("1 + 2 - 3 * 4 / 5 % 6", Log::log));
    }

    @Test
    public void test5() {
        assertEquals(32d, MathInterpreter.eval("0x10 * 2", Log::log));
    }

    @Test
    public void test6() {
        assertEquals(13d, MathInterpreter.eval("0b101 * 0b10 + 0b11", Log::log));
    }

    @Test
    public void test7() {
        assertEquals(6.283185840707965d, MathInterpreter.eval("pi * 2", Log::log));
    }


    @Test
    public void test8() {
        assertThrows(IllegalStateException.class, () -> {
            MathInterpreter.eval("4 * 5 * * 7 bla", Log::log);
        });
    }

    @Test
    public void test9() {
        assertEquals(100000000000000000000d, MathInterpreter.eval("10 ^ 20", Log::log));
    }

    @Test
    public void test10() {
        assertEquals(6d, MathInterpreter.eval("sqrt(3 ^ 2) * 2", Log::log));
    }
}
