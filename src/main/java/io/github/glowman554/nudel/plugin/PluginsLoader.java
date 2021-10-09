package io.github.glowman554.nudel.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PluginsLoader
{
	String plugin_dir;
	ArrayList<PluginLoader> plugins = new ArrayList<PluginLoader>();

	public PluginsLoader(String plugin_dir)
	{
		this.plugin_dir = plugin_dir;

		if (!new File(plugin_dir).exists())
		{
			new File(plugin_dir).mkdir();
		}
	}

	public void load_all() throws IOException
	{
		Files.walk(new File(this.plugin_dir).toPath()).forEach(this::loader);
	}

	private void loader(Path file)
	{
		File f = file.toFile();
		if (!f.isFile())
		{
			//System.out.println(String.format("[%s] Not a file!", f.getName()));
		}
		else
		{
			System.out.println(String.format("[%s] Loading plugin...", f.getName()));
			PluginLoader pl = new PluginLoader(f.getAbsolutePath());
			try
			{
				pl.init();
				System.out.println(String.format("[%s] Loading %s...", f.getName(), pl.toString()));
				pl.instantiate();
				pl.load();

				plugins.add(pl);

				System.out.println(String.format("[%s] Loaded %s!", f.getName(), pl.toString()));
			}
			catch (PluginLoadFail e)
			{
				System.out.println(String.format("[%s] Failed to load plugin: %s", f.getName(), e.getMessage()));
			}
		}
	}
}
