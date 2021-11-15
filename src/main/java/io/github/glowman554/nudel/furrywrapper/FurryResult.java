package io.github.glowman554.nudel.furrywrapper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class FurryResult
{
	public String url;

	public FurryResult(String url)
	{
		this.url = url;
	}

	public void download(String path) throws IOException
	{
		URL url = new URL(this.url);

		// set custom user agent
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

		File destination = new File(path);
		BufferedInputStream in = new BufferedInputStream(conn.getInputStream());		

		FileOutputStream fileOutputStream = new FileOutputStream(destination);
		
		byte dataBuffer[] = new byte[1024];
		int bytesRead;
		while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			fileOutputStream.write(dataBuffer, 0, bytesRead);
		}

		in.close();
		fileOutputStream.close();
	}
}
