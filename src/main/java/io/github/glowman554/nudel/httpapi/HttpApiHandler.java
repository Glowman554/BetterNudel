package io.github.glowman554.nudel.httpapi;

import java.util.Map;

public interface HttpApiHandler
{
	String execute(Map<String, String> query);
}
