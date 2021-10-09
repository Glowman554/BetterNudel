package io.github.glowman554.nudel.plugin;

public class PluginLoadFail extends Exception
{

	public PluginLoadFail(Exception e)
	{
		super("Plugin load fail: " + e.getMessage());
	}
}
