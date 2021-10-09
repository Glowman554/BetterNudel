package io.github.glowman554.nudel.api;

import java.io.IOException;

public class CommitApi extends BaseApi
{
	public String getCommit() throws IOException
	{
		return request("http://whatthecommit.com/index.txt");
	}
}
