package gq.glowman554.bot.command.impl.testing;

import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.log.Log;

public class Testing {
    public static CommandConfig default_config = new CommandConfig("just for testing", "just for testing", "testing");

    public static void load() {
        Log.log("--- WARNING --- Loading testing commands!!!!");

        Main.commandManager.add_command("test_arg", new TestArgCommand());
        Main.commandManager.add_command("test_reply", new TestReplyCommand());
        Main.commandManager.add_command("test_mention_ids", new TestMentionIdsCommand());
        Main.commandManager.add_command("test_files", new TestFilesCommand());
        Main.commandManager.add_command("test_message_delete", new TestMessageDeleteCommand());
        Main.commandManager.add_command("test_message_sender_id", new TestMessageSenderIdCommand());
        Main.commandManager.add_command("test_file_sending", new TestFileSendingCommand());
        Main.commandManager.add_command("test_formatting", new TestFormattingCommand());
        Main.commandManager.add_command("test_chat_name", new TestChatNameCommand());
        Main.commandManager.add_command("crash", new CrashCommand());
    }
}
