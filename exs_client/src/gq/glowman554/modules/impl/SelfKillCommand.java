package gq.glowman554.modules.impl;

import gq.glowman554.modules.Module;

public class SelfKillCommand implements Module {
    public SelfKillCommand() {
    }

    public String execute(String target) {
        (new Thread("kill") {
            public void run() {
                try {
                    Thread.sleep(10000L);
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return null;
    }
}
