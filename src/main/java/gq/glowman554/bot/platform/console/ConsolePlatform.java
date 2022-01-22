package gq.glowman554.bot.platform.console;

import gq.glowman554.bot.Main;
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
                Main.commandManager.on_command(new ConsoleCommandEvent(command, new ConsoleCommandPlatform()));
            } catch (Exception e) {
                Log.log(e.toString());
            }
        }
    }
}
