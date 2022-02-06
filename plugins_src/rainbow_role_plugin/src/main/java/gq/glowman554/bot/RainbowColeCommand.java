package gq.glowman554.bot;

import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.platform.discord.DiscordCommandEvent;

public class RainbowColeCommand implements Command {

	@Override
	public void execute(CommandEvent event) throws Exception {
		if (event instanceof DiscordCommandEvent) {
			if (event.get_arguments().length != 2) {
				event.message_send("Expected 2 arguments!");
			} else {
				String command = event.get_arguments()[0];
				String role = event.get_arguments()[1];

				switch (command) {
					case "add": {
						RainbowRoleManager.instance.add_role(role);
						event.message_send(role + " is now a rainbow role!");
					}
					break;

					case "remove": {
						RainbowRoleManager.instance.remove_role(role);
						event.message_send(role + " is no longer a rainbow role!");
					}
					break;

					default: {
						event.message_send("Unknown command!");
					}
					break;
				}
			}
		} else {
			throw new IllegalCallerException("Command is only available for Discord.");
		}
	}

	@Override
	public CommandConfig get_config() {
		return new CommandConfig("help_short", "help_long", "role");
	}

	@Override
	public void on_register() {
		
	}
	
}
