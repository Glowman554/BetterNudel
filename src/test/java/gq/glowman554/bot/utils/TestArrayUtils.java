package gq.glowman554.bot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestArrayUtils {
    @Test
    public void test1() {
        String[] test_arr = new String[] { "test", "test2", "test3" };

        assertTrue(ArrayUtils.contains(test_arr, "test2"));
        assertFalse(ArrayUtils.contains(test_arr, "test4"));

        test_arr = ArrayUtils.add(test_arr, "test4");

        assertTrue(ArrayUtils.contains(test_arr, "test4"));

        test_arr = ArrayUtils.remove(test_arr, "test4");

        assertFalse(ArrayUtils.contains(test_arr, "test4"));

        assertEquals("test test2 test3", ArrayUtils.stringify(test_arr, " "));
    }
}
