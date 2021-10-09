import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.DogApi;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.JsonSyntaxException;

public class TestDogApi
{
	@Test
	public void testDogApi() throws IOException, JsonSyntaxException
	{
		DogApi api = new DogApi();
		String url = api.getDog();
		System.out.println(url);
		String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(url));
		System.out.println(path);
		api.download(path);
	}
}
