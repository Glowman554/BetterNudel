package io.github.glowman554.nudel.api;

import java.io.IOException;

public class GoogleTtsApi extends BaseApi
{
	public String _url;
	public String _lang = "en";

	public String getTts(String text)
	{
		text = text.replace(" ", "%20");

		if (text.length() > 200)
		{
			throw new IllegalArgumentException("Text is too long");
		}

		_url = "https://translate.google.com/translate_tts?ie=UTF-8&q=" + text + "&tl=" + _lang + "&total=1&idx=0&textlen=" + text.length() + "&client=tw-ob";
		return _url;
	}

	public void download(String path) throws IOException
	{
		download(path, _url);
	}

	public void setLang(String lang)
	{
		_lang = lang;
	}
}
