package gq.glowman554.bot.log;

import gq.glowman554.bot.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class Log {
    private static String current_log_file = "./logs/" + System.currentTimeMillis() + ".log";

    public static void log(String message) {
        synchronized (Log.class) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            String new_message = "";
            for (String line : message.split("\n")) {
                new_message += String.format("[%s::%s at %s:%s] %s\n", stackTraceElements[2].getClassName(), stackTraceElements[2].getMethodName(), stackTraceElements[2].getFileName(), stackTraceElements[2].getLineNumber(), line);
            }

            new_message = new_message.strip();

            if (!new File("./logs").isDirectory()) {
                new File("./logs").mkdir();
            }

            if (!new File(current_log_file).exists()) {
                try {
                    FileUtils.writeFile(current_log_file, "---log start---");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                FileUtils.appendFile(current_log_file, "\n" + new_message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(new_message);
        }
    }

    public static String getCurrent_log_file() {
        return current_log_file;
    }
}
