import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

		InputStream is = new File("test.txt").toURI().toURL().openStream();

		assertEquals("hello world", FileUtils.readFile(is));
	}
}
