package io.github.glowman554.nudel.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import io.github.glowman554.nudel.api.BaseApi;

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

	public void load_from_url_or_path(String url_or_path) throws IOException
	{
		String regex = "^(http|https):\\/\\/([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$";
		if (url_or_path.matches(regex))
		{
			// URL
			String download_path = this.plugin_dir + "/" + url_or_path.substring(url_or_path.lastIndexOf("/") + 1);
			System.out.println("Downloading " + url_or_path + " to " + download_path);

			BaseApi api = new BaseApi();
			api.download(download_path, url_or_path);

			loader(new File(download_path).toPath());
		}
		else
		{
			// Path
			loader(new File(url_or_path).toPath());
		}
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
