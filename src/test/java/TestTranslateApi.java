import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.TranslateApi;
import net.shadew.json.JsonSyntaxException;

public class TestTranslateApi
{
	@Test
	public void testTranslateApi() throws IOException, JsonSyntaxException
	{
		TranslateApi api = new TranslateApi();
		
		String response = api.translate("Hallo wie heißen sie?", "en");

		System.out.println(response);
	}
}
