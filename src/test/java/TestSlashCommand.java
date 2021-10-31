import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.glowman554.nudel.discord.commands.SlashCommand;
import io.github.glowman554.nudel.discord.commands.SlashCommandParameter;

public class TestSlashCommand
{
	@Test
	public void testSlashCommandParameter()
	{
		SlashCommandParameter slashCommandParameter = new SlashCommandParameter("test", "test_something", SlashCommandParameter.STRING, true);
		
		System.out.println(slashCommandParameter.toString());

		SlashCommandParameter slashCommandParameter2 = new SlashCommandParameter("test2", "test_something2", SlashCommandParameter.STRING, true, new String[] {
			"name des tires",
			"name des tires2"
		});

		System.out.println(slashCommandParameter2.toString());

		SlashCommand slashCommand = new SlashCommand("test", "test_something", SlashCommand.CHAT_INPUT, new SlashCommandParameter[] { slashCommandParameter, slashCommandParameter2 });

		System.out.println(slashCommand.toString());
	}
}
