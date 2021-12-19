package gq.glowman554;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class FileUtils {
    public FileUtils() {
    }

    public static String readFile(String file_name) throws IOException {
        FileReader fr = new FileReader(file_name);
        StringWriter out = new StringWriter();
        char[] buf = new char[4096];

        int read;
        while((read = fr.read(buf)) != -1) {
            out.write(buf, 0, read);
        }

        fr.close();
        return out.toString();
    }

    public static void writeFile(String file_name, String file_contents) throws IOException {
        FileWriter fw = new FileWriter(file_name);
        fw.write(file_contents);
        fw.close();
    }

    public static String getFileExtension(String file_name) {
        int dot = file_name.lastIndexOf(46);
        if (dot == -1) {
            return "";
        } else {
            String extension = file_name.substring(dot + 1);
            return extension;
        }
    }
}
