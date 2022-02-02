package gq.glowman554.utils;

import gq.glowman554.Entry;
import gq.glowman554.Exs;

import java.util.ArrayList;
import java.util.Base64;

public class Log {
    private static boolean debug = true;

    private static ArrayList<String> log_queue = new ArrayList<>();

    public static void log(String msg) {
        if (debug) {
            System.out.println(msg);
        }

        log_queue.add(msg);
    }

    public static void log(Exception e) {
        if (debug) {
            e.printStackTrace();
        }

        log_queue.add(e.getClass().getSimpleName() + ": " + e.getMessage());
    }

    public static void init() {
        new Thread(() -> {
           while (true) {
               try {
                   if (log_queue.size() != 0) {
                       String msg = log_queue.remove(0);
                       if (debug) {
                           System.out.println("(sending log msg: \"" + msg + "\" remaining queue size: " + log_queue.size() + ")");
                       }
                       HttpUtils.request(Exs.base_url + "/log?text=" + Base64.getEncoder().encodeToString(msg.getBytes()) + (Entry.id == null ? "" : "&id=" + Entry.id));
                   }
                   Thread.sleep(100);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        }).start();
    }
}
