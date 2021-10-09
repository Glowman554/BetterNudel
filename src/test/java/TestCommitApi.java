import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.api.CommitApi;

public class TestCommitApi
{
	@Test
	public void testCommitApi() throws IOException
	{
		CommitApi commitApi = new CommitApi();
		String commit = commitApi.getCommit();
		System.out.println(commit);
	}
}
