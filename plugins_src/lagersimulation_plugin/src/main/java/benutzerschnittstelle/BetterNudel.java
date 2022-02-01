package benutzerschnittstelle;


import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;

public class BetterNudel implements Plugin
{

	@Override
	public void on_load() throws Exception
	{
		Log.log("Hello big gay world!");
		Main.commandManager.add_command("lagersimulation", new BetterNudelCommand());
	}

}
