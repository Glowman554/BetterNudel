package io.github.glowman554.nudel.plugin;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

@SuppressWarnings({ "unchecked", "deprecation" })
public class PluginLoader
{
	String jar_path;

	URLClassLoader child;
	Class<? extends Plugin> class1;
	Plugin plugin;
	JsonNode plugin_info_root;

	public PluginLoader(String jar_path)
	{
		this.jar_path = jar_path;
	}

	public void init() throws PluginLoadFail
	{
		try
		{
			this.child = new URLClassLoader(new URL[] { new URL("jar:file:" + jar_path + "!/") }, this.getClass().getClassLoader());

			InputStream plugin_info = this.child.getResourceAsStream("plugin.json");
			String plugin_json = FileUtils.readFile(plugin_info);

			Json _json = Json.json();
			this.plugin_info_root = _json.parse(plugin_json);

			this.class1 = (Class<? extends Plugin>) this.child.loadClass(this.plugin_info_root.get("main").asString());
		}
		catch (Exception e)
		{
			throw new PluginLoadFail(e);
		}
	}

	public void instantiate() throws PluginLoadFail
	{
		try
		{
			this.plugin = this.class1.newInstance();
		}
		catch (Exception e)
		{
			throw new PluginLoadFail(e);
		}
	}

	public void load() throws PluginLoadFail
	{
		try
		{
			this.plugin.on_load();
		}
		catch (Exception e)
		{
			throw new PluginLoadFail(e);
		}
	}

	@Override
	public String toString()
	{
		return this.plugin_info_root.get("name").asString() + "@" + this.plugin_info_root.get("version").asString();
	}
}
