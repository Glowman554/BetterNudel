import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.exec.ExecutionEngine;
import io.github.glowman554.nudel.exec.ExecutionEngineError;

public class TestExecutionEngine
{
	@Test
	public void testExecutionEngine() throws ExecutionEngineError, IOException
	{
		ExecutionEngine ee = new ExecutionEngine(false);
		String result = ee.execute("echo test");

		System.out.println(result);

		assertEquals("test", result);
	}
}
