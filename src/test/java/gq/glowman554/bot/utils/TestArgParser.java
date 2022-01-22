package gq.glowman554.bot.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestArgParser {
    @Test
    public void test1() {
        String[] test_args = { "java", "--test=1", "--test=2", "--other-test", "--other-test=1", "--test123", "--test123=aa" };
        ArgParser arg_parser = new ArgParser(test_args);
        arg_parser.parse();

        assertEquals("1", arg_parser.consume_option("--test"));
        assertEquals("2", arg_parser.consume_option("--test"));

        assertEquals("a", arg_parser.consume_option("--other-test", "a"));
        assertEquals("1", arg_parser.consume_option("--other-test", "a"));

        assertTrue(arg_parser.is_option("--test123"));
        assertEquals("test", arg_parser.consume_option("--test123", "test"));
        assertEquals("aa", arg_parser.consume_option("--test123", "test"));
        assertEquals("abc", arg_parser.consume_option("--abc", "abc"));
    }
}
