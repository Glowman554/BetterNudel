package gq.glowman554.bot.platform.discord;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class DiscordPlatform {
    public JDA jda;
    private String current_rp;

    public DiscordPlatform() throws LoginException, InterruptedException {
        Log.log("Discord platform starting...");

        JDABuilder jdaBuilder = JDABuilder.createDefault(Main.configManager.get_key_as_str("discord_token")).setMemberCachePolicy(MemberCachePolicy.ALL);

        for (GatewayIntent value : GatewayIntent.values()) {
            Log.log("enabling: " + value);
            jdaBuilder.enableIntents(value);
        }

        jda = jdaBuilder.build();

        jda.awaitReady();

        jda.addEventListener(new DiscordLogger());
        jda.addEventListener(new DiscordReceiver());

        this.setDefaultRP();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                updateRP();
            }
        }).start();
    }

    public void setDefaultRP() {
        this.current_rp = Main.commandManager.prefix + "help";
        jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.current_rp, "https://www.twitch.tv/glowman434"));
    }

    public void setRP(String rp) {
        this.current_rp = rp;
        jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.current_rp, "https://www.twitch.tv/glowman434"));
    }

    public void updateRP() {
        jda.getPresence().setActivity(net.dv8tion.jda.api.entities.Activity.streaming(this.current_rp, "https://www.twitch.tv/glowman434"));
    }
}
