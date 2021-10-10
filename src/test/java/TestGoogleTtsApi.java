import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.GoogleTtsApi;
import io.github.glowman554.nudel.utils.FileUtils;

public class TestGoogleTtsApi
{
	@Test
	public void testGoogleTtsApi() throws IOException
	{
		GoogleTtsApi googleTtsApi = new GoogleTtsApi();
		String res = googleTtsApi.getTts("Hello world");
		System.out.println(res);

		String path = FileUtils.randomTmpFile("mp3");
		System.out.println(path);
		googleTtsApi.download(path);
	}

	@Test
	public void testGoogleTtsApiLang() throws IOException
	{
		GoogleTtsApi googleTtsApi = new GoogleTtsApi();
		googleTtsApi.setLang("de");
		String res = googleTtsApi.getTts("Hallo welt");
		System.out.println(res);

		String path = FileUtils.randomTmpFile("mp3");
		System.out.println(path);
		googleTtsApi.download(path);
	}
}
