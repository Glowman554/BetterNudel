package benutzerschnittstelle;

import fachkonzept.Person;
import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;

public class BetterNudel implements Plugin {
    @Override
    public void on_load() throws Exception {
        Log.log("Hello big gay world!");

        Main.commandManager.add_command("bmi", new Command() {
            @Override
            public CommandConfig get_config() {
                return new CommandConfig("Calculate your bmi!", String.format("Use '%sbmi [weight][size]' to calculate your bmi!", Main.commandManager.prefix), null);
            }

            @Override
            public void execute(CommandEvent commandEvent) throws Exception {
                if (commandEvent.get_arguments().length != 2) {
                    commandEvent.message_send("Expected 2 arguments!");
                } else {
                    int weight = Integer.parseInt(commandEvent.get_arguments()[0]);
                    double size = Double.parseDouble(commandEvent.get_arguments()[1]);

                    Person person = new Person(weight, size);

                    commandEvent.message_send(String.format("BMI: %s (%s)", person.berechneBMI(), person.ermittleGewichtsklasse()));
                }
            }

            @Override
            public void on_register() {

            }
        });
    }
}
