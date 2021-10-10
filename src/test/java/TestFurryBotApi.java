import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.furrywrapper.FurryBotApi;


public class TestFurryBotApi
{
	//@Test
	public void testFurryBotApiFull() throws InterruptedException
	{
		FurryBotApi api = new FurryBotApi();
		assertNotNull(api);

		for (String i : api._methods)
		{
			String result = null;
			try
			{
				result = api.random_image(i).url;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			assertNotNull(result);

			System.out.println(i + ": " + result);
			Thread.sleep(1000);
		}
	}

	@Test
	public void testFurryBotApi() throws InterruptedException
	{
		FurryBotApi api = new FurryBotApi();
		assertNotNull(api);

		String result = null;
		try
		{
			result = api.furry_fursuit().url;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		assertNotNull(result);

		System.out.println(result);
	}
}
