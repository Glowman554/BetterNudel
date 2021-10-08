package io.github.glowman554.nudel.furrywrapper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

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
		File destination = new File(path);
		BufferedInputStream in = new BufferedInputStream(url.openStream());

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
