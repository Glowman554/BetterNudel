package gq.glowman554.bot.utils;

public class TimeUtils {
    public static String second_to_dhms(int seconds) {
        int day = (int) Math.floor(seconds / (3600 * 24));
        int hour = (int) Math.floor(seconds % (3600 * 24) / 3600);
        int minute = (int) Math.floor(seconds % 3600 / 60);
        int second = (int) Math.floor(seconds % 60);

        String day_s = day > 0 ? day + (day == 1 ? " day, " : " days, ") : "";
        String hour_s = hour > 0 ? hour + (hour == 1 ? " hour, " : " hours, ") : "";
        String minute_s = minute > 0 ? minute + (minute == 1 ? " minute, " : " minutes, ") : "";
        String second_s = second > 0 ? second + (second == 1 ? " second" : " seconds") : "";

        StringBuilder return_ = new StringBuilder((day_s + hour_s + minute_s + second_s).strip());

        if (return_.charAt(return_.length() - 1) == ',') {
            return_.deleteCharAt(return_.length() - 1);
        }

        return return_.toString();
    }

    public static String millisecond_to_dhms(long milliseconds) {
        return second_to_dhms((int) (milliseconds / 1000));
    }
}
