package gq.glowman554.bot.platform.console;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.MultiThreadHelper;

import java.util.Scanner;

public class ConsolePlatform {

    public ConsolePlatform() {
        Log.log("Console platform starting...");

        // ----------------------------------------------------
        // IMPORTANT: Add every possible permission here!!!!!!!
        // ----------------------------------------------------
        Main.commandManager.permissionManager.add_permission("<stdin>", "testing");
        Main.commandManager.permissionManager.add_permission("<stdin>", "role");
        Main.commandManager.permissionManager.add_permission("<stdin>", "exec");
        Main.commandManager.permissionManager.add_permission("<stdin>", "im_18");
        Main.commandManager.permissionManager.add_permission("<stdin>", "no_limit");
        Main.commandManager.permissionManager.add_permission("<stdin>", "log");
        Main.commandManager.permissionManager.add_permission("<stdin>", "compile");
        Main.commandManager.permissionManager.add_permission("<stdin>", "full_access");
        Main.commandManager.permissionManager.add_permission("<stdin>", "token");

        MultiThreadHelper.run(this::run);
    }

    public void run() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = input.nextLine();

            if (command == null) {
                continue;
            }

            ConsoleCommandEvent commandEvent = new ConsoleCommandEvent(command, new ConsoleCommandPlatform());

            MessageEvent event = new MessageEvent(commandEvent);
            event.call();

            if (event.isCanceled()) {
                continue;
            }

            try {
                Main.commandManager.on_command(commandEvent);
            } catch (Exception e) {
                commandEvent.handle_exception(e);
            }
        }
    }
}
