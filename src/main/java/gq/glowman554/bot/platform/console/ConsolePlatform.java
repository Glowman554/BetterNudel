package gq.glowman554.bot.platform.console;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.log.Log;

import java.util.Scanner;

public class ConsolePlatform extends Thread {

    public ConsolePlatform() {
        Log.log("Console platform starting...");

        // ----------------------------------------------------
        // IMPORTANT: Add every possible permission here!!!!!!!
        // ----------------------------------------------------
        Main.commandManager.permissionManager.add_permission("<stdin>", "testing");
        Main.commandManager.permissionManager.add_permission("<stdin>", "role");

        this.start();
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = input.nextLine();

            if (command == null) {
                continue;
            }

            try {
                ConsoleCommandEvent commandEvent = new ConsoleCommandEvent(command, new ConsoleCommandPlatform());

                MessageEvent event = new MessageEvent(commandEvent);
                event.call();

                if (event.isCanceled()) {
                    continue;
                }

                Main.commandManager.on_command(commandEvent);
            } catch (Exception e) {
                Log.log(e.toString());
            }
        }
    }
}
