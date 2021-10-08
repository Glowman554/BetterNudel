import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.utils.FileUtils;

public class TestFileUtils
{
	@Test
	public void testFileUtils() throws IOException
	{
		FileUtils.writeFile("test.txt", "hello world");
		assertEquals("hello world", FileUtils.readFile("test.txt"));

		assertEquals("txt", FileUtils.getFileExtension("test.txt"));
	}
}
