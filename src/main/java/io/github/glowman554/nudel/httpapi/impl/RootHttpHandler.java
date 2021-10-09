package io.github.glowman554.nudel.httpapi.impl;

import java.util.Map;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;

public class RootHttpHandler implements HttpApiHandler
{

	@Override
	public String execute(Map<String, String> query)
	{
		return "Hello World!";
	}
	
}
