package io.github.glowman554.nudel.plugin;

import java.io.File;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import io.github.glowman554.nudel.utils.FileUtils;

public class JSPlugin implements Plugin
{
	String file_content = "";
	String file = null;

	private ScriptEngine scriptEngine;
	private Invocable scriptEval;
	
	private String injected = "var on_load = function() { throw new Error('Please specify a on_load function like \\'on_load = function() {}\\''); };\nvar api = Java.type('io.github.glowman554.nudel.plugin.JSPluginApi');\nvar main = Java.type('io.github.glowman554.nudel.Main');\nvar discord = Java.type('io.github.glowman554.nudel.discord.Discord').discord;\nvar file = Java.type('io.github.glowman554.nudel.utils.FileUtils');\n";
	
	public JSPlugin(String file) throws ScriptException
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
	
	private void _init() throws ScriptException
	{
		this.scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
		
		this.scriptEngine.eval(injected + this.file_content);
		
		this.scriptEval = (Invocable) this.scriptEngine;
	}
	
	@Override
	public void on_load() throws Exception
	{
		this.scriptEval.invokeFunction("on_load");
	}

}
