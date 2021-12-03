import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.IpInfoApi;
import io.github.glowman554.nudel.api.IpInfoApi.IpInfo;
import net.shadew.json.Json;
import net.shadew.json.JsonSyntaxException;

public class TestIpInfoApi
{
	@Test
	public void testIpInfoApi() throws IOException, JsonSyntaxException
	{
		IpInfo i = new IpInfoApi().request_info("8.8.8.8");
		System.out.println(i.toString());

		Json json = Json.json();
		System.out.println(json.serialize(i.toJson()));
	}
}
