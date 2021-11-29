package io.github.glowman554.nudel.plugin;

import java.util.Map;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import io.github.glowman554.nudel.httpapi.HttpApiHandler;

public class JSHttpHandler implements HttpApiHandler
{
	public ScriptObjectMirror handler;

	@Override
	public String execute(Map<String, String> query) throws Exception
	{
		return (String) handler.call(handler, query);
	}
	
}
