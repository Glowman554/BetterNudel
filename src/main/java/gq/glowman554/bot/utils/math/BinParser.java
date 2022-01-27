package gq.glowman554.bot.utils.math;

public class BinParser {
    private static int bin_digit(char digit) {
        int num_digit = "01".indexOf(digit);

        if (num_digit == -1) {
            throw new IllegalArgumentException("Character " + digit + " not recognized!");
        }

        return num_digit;
    }
    public static int from_bin(String bin) {
        if (bin.startsWith("0b")) {
            bin = bin.substring(2);
        }

        bin = new StringBuilder(bin).reverse().toString();

        int idx = 0;
        int ret = 0;

        for (String s : bin.split("")) {
            char digit = s.charAt(0);
            int num_digit = bin_digit(digit);

            ret += Math.pow(2, idx++) * num_digit;
        }

        return ret;
    }
}
