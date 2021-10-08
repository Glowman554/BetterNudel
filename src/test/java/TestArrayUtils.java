import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.utils.ArrayUtils;

public class TestArrayUtils
{
	@Test
	public void testArrayUtils()
	{
		String[] test_arr = new String[] { "test", "test2", "test3" };

		assertEquals(true, ArrayUtils.contains(test_arr, "test2"));
		assertEquals(false, ArrayUtils.contains(test_arr, "test4"));

		test_arr = ArrayUtils.add(test_arr, "test4");

		assertEquals(true, ArrayUtils.contains(test_arr, "test4"));
		
		test_arr = ArrayUtils.remove(test_arr, "test4");

		assertEquals(false, ArrayUtils.contains(test_arr, "test4"));

		assertEquals("test test2 test3", ArrayUtils.stringify(test_arr, " "));
	}
}
