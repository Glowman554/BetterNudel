package gq.glowman554.bot.platform.teleram;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.log.Log;
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

                try {
                    Main.commandManager.on_command(commandEvent);
                } catch (Exception e) {
                    commandEvent.message_send(commandEvent.command_platform.format_code_block(e.getClass().getSimpleName() + ": " + e.getMessage()));
                }
            }

            if (update.getMessage().getCaption() != null) {
                TelegramCommandEvent commandEvent = new TelegramCommandEvent(update.getMessage().getCaption(), new TelegramCommandPlatform(), update, this);

                try {
                    Main.commandManager.on_command(commandEvent);
                } catch (Exception e) {
                    commandEvent.message_send(commandEvent.command_platform.format_code_block(e.getClass().getSimpleName() + ": " + e.getMessage()));
                }
            }
        }
    }
}
