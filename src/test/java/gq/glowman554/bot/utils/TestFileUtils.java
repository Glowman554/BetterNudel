package gq.glowman554.bot.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFileUtils {
    @Test
    public void test1() throws IOException {
        FileUtils.writeFile("test.txt", "hello world");
        assertEquals("hello world", FileUtils.readFile("test.txt"));

        assertEquals("txt", FileUtils.getFileExtension("test.txt"));

        InputStream is = new File("test.txt").toURI().toURL().openStream();

        assertEquals("hello world", FileUtils.readFile(is));

        FileUtils.appendFile("test.txt", " 2");

        assertEquals("hello world 2", FileUtils.readFile("test.txt"));
    }
}
