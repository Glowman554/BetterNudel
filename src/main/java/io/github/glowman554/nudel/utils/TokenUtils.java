package io.github.glowman554.nudel.utils;

import io.github.glowman554.nudel.httpapi.impl.auth.AuthManager;

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

	public static String checkToken(String token, AuthManager authManager)
	{
		if (token == null)
		{
			throw new IllegalArgumentException("Token cannot be null");
		}

		if (authManager.get_user_by_token(token) == null)
		{
			throw new IllegalArgumentException("Invalid token");
		}

		return authManager.get_user_by_token(token);
	}
}
