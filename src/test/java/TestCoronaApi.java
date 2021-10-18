import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.CoronaApi;
import net.shadew.json.JsonSyntaxException;

public class TestCoronaApi
{
	@Test
	public void testCoronaApi() throws IOException, JsonSyntaxException
	{
		CoronaApi coronaApi = new CoronaApi();
		CoronaApi.CoronaApiResult result = coronaApi.fetchCountry("germany");
		
		System.out.println(result.toString());
	}

	@Test
	public void testCoronaApi2() throws IOException, JsonSyntaxException
	{
		CoronaApi coronaApi = new CoronaApi();
		CoronaApi.CoronaApiResult result = coronaApi.fetchCountry("usa");
		
		System.out.println(result.toString());
	}

	@Test
	public void testCoronaApiInvalid() throws IOException, JsonSyntaxException
	{
		assertThrows(FileNotFoundException.class, () -> {
			CoronaApi coronaApi = new CoronaApi();
			CoronaApi.CoronaApiResult result = coronaApi.fetchCountry("invalid");
			
			System.out.println(result.toString());
		});
	}
	
	// currently 502 no idea why
	// fixed by using a different api lol apparently the old one is deprecated or something idk
}
