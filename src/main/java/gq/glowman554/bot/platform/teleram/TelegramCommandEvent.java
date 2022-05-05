package gq.glowman554.bot.platform.teleram;

import gq.glowman554.bot.command.CommandEvent;
import gq.glowman554.bot.command.CommandPlatform;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.FileUtils;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class TelegramCommandEvent extends CommandEvent {
    private final Update update;
    private final TelegramPlatform telegramPlatform;

    public TelegramCommandEvent(String message, CommandPlatform command_platform, Update update, TelegramPlatform telegramPlatform) {
        super(message, command_platform);
        this.update = update;
        this.telegramPlatform = telegramPlatform;
    }


    @Override
    public void message_send(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(message);

        sendMessage.enableHtml(((TelegramCommandPlatform) command_platform).reply_with_html);

        try {
            telegramPlatform.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    @Override
    public void message_quote(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
        sendMessage.setText(message);

        sendMessage.enableHtml(((TelegramCommandPlatform) command_platform).reply_with_html);

        try {
            telegramPlatform.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public String[] get_mention_ids() {
        String[] mentions = new String[0];

        for (MessageEntity entity : update.getMessage().getEntities()) {
            if (entity.getType().equals("mention")) {
                if (update.getMessage().getCaption() == null) {
                    String substr = update.getMessage().getText().substring(entity.getOffset(), entity.getOffset() + entity.getLength());
                    mentions = ArrayUtils.add(mentions, substr);
                } else {
                    String substr = update.getMessage().getCaption().substring(entity.getOffset(), entity.getOffset() + entity.getLength());
                    mentions = ArrayUtils.add(mentions, substr);
                }
            }
        }

        return mentions;
    }

    @Override
    public int get_num_files() {
        int num_files = 0;

        if (update.getMessage().getAudio() != null) {
            num_files++;
        }

        if (update.getMessage().getVideo() != null) {
            num_files++;
        }

        if (update.getMessage().getDocument() != null) {
            num_files++;
        }

        if (update.getMessage().getPhoto() != null) {
            num_files += update.getMessage().getPhoto().size();
        }

        return num_files;
    }

    private org.telegram.telegrambots.meta.api.objects.File get_file(String file_id) {
        Log.log("Getting file for " + file_id);
        GetFile getFile = new GetFile();
        getFile.setFileId(file_id);

        try {
            org.telegram.telegrambots.meta.api.objects.File file = telegramPlatform.execute(getFile);
            return file;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public String get_file(int idx) {
        TelegramFileDownloader telegramFileDownloader = new TelegramFileDownloader(telegramPlatform::getBotToken);

        if (update.getMessage().getAudio() != null) {
            idx--;
            if (idx == -1) {
                String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(update.getMessage().getAudio().getFileName()));
                try {
                    telegramFileDownloader.downloadFile(get_file(update.getMessage().getAudio().getFileId()), new File(path));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e.getMessage());
                }

                return path;
            }
        }

        if (update.getMessage().getVideo() != null) {
            idx--;
            if (idx == -1) {
                String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(update.getMessage().getVideo().getFileName()));
                try {
                    telegramFileDownloader.downloadFile(get_file(update.getMessage().getVideo().getFileId()), new File(path));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e.getMessage());
                }

                return path;
            }
        }

        if (update.getMessage().getDocument() != null) {
            idx--;
            if (idx == -1) {
                String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(update.getMessage().getDocument().getFileName()));
                try {
                    telegramFileDownloader.downloadFile(get_file(update.getMessage().getDocument().getFileId()), new File(path));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e.getMessage());
                }

                return path;
            }
        }

        if (update.getMessage().getPhoto() != null) {
            for (PhotoSize photoSize : update.getMessage().getPhoto()) {
                idx--;
                if (idx == -1) {
                    String path = FileUtils.randomTmpFile(FileUtils.getFileExtension(get_file(photoSize.getFileId()).getFileUrl(telegramPlatform.getBotToken())));
                    try {
                        telegramFileDownloader.downloadFile(get_file(photoSize.getFileId()), new File(path));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        throw new IllegalStateException(e.getMessage());
                    }

                    return path;
                }
            }
        }
        return null;
    }

    @Override
    public void message_delete() {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(update.getMessage().getMessageId());
        deleteMessage.setChatId(update.getMessage().getChatId().toString());

        try {
            telegramPlatform.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_picture(File file) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId().toString());
        sendPhoto.setPhoto(new InputFile(file));

        try {
            telegramPlatform.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_audio(File file) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(update.getMessage().getChatId().toString());
        sendAudio.setAudio(new InputFile(file));

        try {
            telegramPlatform.execute(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_video(File file) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(update.getMessage().getChatId().toString());
        sendVideo.setVideo(new InputFile(file));

        try {
            telegramPlatform.execute(sendVideo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void send_file(File file) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(update.getMessage().getChatId().toString());
        sendDocument.setDocument(new InputFile(file));

        try {
            telegramPlatform.execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public String get_sender_id() {
        return update.getMessage().getFrom().getUserName() != null ? "@" + update.getMessage().getFrom().getUserName() : update.getMessage().getFrom().getId().toString();
    }

    @Override
    public String get_chat_name() {
        String chat_name = "";
        if (update.getMessage().getChat().getUserName() == null) {
            chat_name = update.getMessage().getChat().getTitle();
        } else {
            chat_name = update.getMessage().getChat().getUserName();
        }

        return chat_name;
    }
}
