package gq.glowman554.bot.platform.web;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.log.Log;

public class WebPlatform {
    /*
    -test_formatting - working
    -test_message_sender_id - working
    -test_arg - working
    -test_files - working
    -test_file_sending - working
    -test_reply - working
    -test_mention_ids - working
    -test_message_delete - working
    -test_chat_name - working
     */

    public WebPlatform() {
        Log.log("Web platform starting...");

        new WebPlatformHandler(HttpApi.instance, "/api/v2/web");
    }
}
