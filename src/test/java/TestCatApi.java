import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.CatApi;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.JsonSyntaxException;

public class TestCatApi
{
	@Test
	public void testCatApi() throws IOException, JsonSyntaxException
	{
		CatApi api = new CatApi();
		String url = api.getCat();
		System.out.println(url);
		String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(url));
		System.out.println(path);
		api.download(path);
	}
}
