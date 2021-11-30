package io.github.glowman554.nudel.plugin;

import java.io.File;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.utils.FileUtils;

public class JSPlugin implements Plugin
{
	String file_content = "";
	String file = null;

	private ScriptEngine scriptEngine;
	private Invocable scriptEval;
	private String injected = "";
	
	public JSPlugin(String file) throws ScriptException, IOException
	{
		this.file_content = file;
		this._init();
	}
	
	public JSPlugin(File file) throws IOException, ScriptException
	{
		this.file = file.getAbsolutePath();
		this.file_content = FileUtils.readFile(this.file);
		this._init();
	}
	
	private void _init() throws ScriptException, IOException
	{
		this.injected = FileUtils.readFile(Main.class.getResourceAsStream("/jsplugin_bootstrap.js"));
		this.injected = injected.replaceAll("\n", " ");
		this.injected = injected.replaceAll("\r", " ");
		this.injected = injected.replaceAll("\t", " ");

		this.scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
		
		this.scriptEngine.eval(injected + this.file_content);
		
		this.scriptEval = (Invocable) this.scriptEngine;
	}
	
	@Override
	public void on_load() throws Exception
	{
		if ((boolean) this.scriptEval.invokeFunction("bootstrap"))
		{
			this.scriptEval.invokeFunction("on_load");
		}
		else
		{
			System.out.printf("[%s] Plugin failed to load. Bootstrap failed.\n", this.file);
		}
	}

}
