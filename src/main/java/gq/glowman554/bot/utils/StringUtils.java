package gq.glowman554.bot.utils;

public class StringUtils {
    public static String[] partition(String in, int max_len) {
        if (in.length() > max_len) {
            if (!in.endsWith("\n")) {
                in += "\n";
            }

            String[] res = new String[0];

            int numChunks = (int) Math.ceil(in.length() / (float) max_len);

            for (int i = 0, o = 0; i < numChunks; i++) {
                int x = in.length() < o + max_len ? in.length() - o - 1 : max_len;

                while (in.charAt(o + x) != '\n') {
                    x--;
                }

                res = ArrayUtils.add(res, in.substring(o, o + x));

                o += x;
            }

            return res;
        } else {
            return new String[]{in};
        }
    }
}
