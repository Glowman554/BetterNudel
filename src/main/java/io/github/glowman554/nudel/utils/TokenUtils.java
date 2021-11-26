package io.github.glowman554.nudel.utils;

public class TokenUtils
{
	public static void checkToken(String token)
	{
		if (System.getenv("TOKEN") == null)
		{
			System.out.println("WARNING: No token found, please set TOKEN environment variable");
			return;
		}

		if (token == null)
		{
			throw new IllegalArgumentException("Token cannot be null");
		}

		if (!token.equals(System.getenv("TOKEN")))
		{
			throw new IllegalArgumentException("Invalid token");
		}
	}
}
