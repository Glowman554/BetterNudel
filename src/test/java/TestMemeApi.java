import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.MemeApi;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.JsonSyntaxException;

public class TestMemeApi
{
	@Test
	public void testFoxApi() throws IOException, JsonSyntaxException
	{
		MemeApi api = new MemeApi();
		String url = api.getMeme();
		System.out.println(url);
		String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(url));
		System.out.println(path);
		api.download(path);
	}
}
