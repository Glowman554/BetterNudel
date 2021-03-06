package gq.glowman554.bot.platform.teleram;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.MultiThreadHelper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramPlatform extends TelegramLongPollingBot {
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
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

    public TelegramPlatform() throws TelegramApiException {
        Log.log("Telegram platform starting...");

        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return "null";
    }

    @Override
    public String getBotToken() {
        return Main.configManager.get_key_as_str("telegram_token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Log.log(update.toString());

        if (update.hasMessage()) {
            if (update.getMessage().getText() != null) {
                TelegramCommandEvent commandEvent = new TelegramCommandEvent(update.getMessage().getText(), new TelegramCommandPlatform(), update, this);

                MessageEvent event = new MessageEvent(commandEvent);
                event.call();

                if (event.isCanceled()) {
                    return;
                }

                MultiThreadHelper.run(() -> {
                    try {
                        Main.commandManager.on_command(commandEvent);
                    } catch (Exception e) {
                        commandEvent.handle_exception(e);
                    }
                });
            }

            if (update.getMessage().getCaption() != null) {
                TelegramCommandEvent commandEvent = new TelegramCommandEvent(update.getMessage().getCaption(), new TelegramCommandPlatform(), update, this);

                MessageEvent event = new MessageEvent(commandEvent);
                event.call();

                if (event.isCanceled()) {
                    return;
                }

                MultiThreadHelper.run(() -> {
                    try {
                        Main.commandManager.on_command(commandEvent);
                    } catch (Exception e) {
                        commandEvent.handle_exception(e);
                    }
                });
            }
        }
    }
}
