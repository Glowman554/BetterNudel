package io.github.glowman554.nudel.discord.commands.impl;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.discord.commands.*;
import io.github.glowman554.nudel.utils.ArrayUtils;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class CreatePoolCommand extends ListenerAdapter implements Command, SlashCommand {

    private String config_file = "pool.json";
    private JsonNode config_content = null;

    private Emote yes = null;
    private Emote no = null;

    @Override
    public void execute(CommandEvent event) throws Exception {
        if (event.args.length == 0) {
            event.commandFail("Please add a pool description!");
        } else {
            String pool_description = ArrayUtils.stringify(event.args, " ");

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Pool");
            eb.setDescription(pool_description);
            eb.addBlankField(false);
            eb.addField("Help", "React with " + yes.getAsMention() + " to vote for yes and react with " + no.getAsMention() + " to vote for no!", false);

            Message result = event.event.getChannel().sendMessage(eb.build()).complete();
            result.addReaction(yes).complete();
            result.addReaction(no).complete();

            config_content.add(result.getId());
            save();
        }
    }

    @Override
    public void on_register() {
        Json json = Json.json();

        if (new File(config_file).exists()) {
            try {
                String file_content = FileUtils.readFile(config_file);

                config_content = json.parse(file_content);
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        } else {
            config_content = JsonNode.array();

            try {
                FileUtils.writeFile(config_file, json.serialize(config_content));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Discord.discord.jda.addEventListener(this);

        yes = Discord.discord.jda.getEmoteById("931453753775775805");
        no = Discord.discord.jda.getEmoteById("931453827775860776");
    }

    public void save() throws IOException {
        Json json = Json.json();
        FileUtils.writeFile(config_file, json.serialize(config_content));
    }

    @Override
    public String get_short_help() {
        return "Create pools!";
    }

    @Override
    public String get_long_help() {
        return String.format("Use '%screate_pool [pool description]' to create a pool!", Discord.discord.commandManager.prefix);
    }

    @Override
    public void execute(SlashCommandEvent event) throws Exception {
        event.reply("Please wait...").complete();

        Message result = event.getHook().editOriginal(String.format("**Pool**\n\n%s\n\n**Help**\nReact with %s to vote for yes and react with %s to vote for no!", event.getOption("description").getAsString(), yes.getAsMention(), no.getAsMention())).complete();

        result.addReaction(yes).complete();
        result.addReaction(no).complete();

        config_content.add(result.getId());
        save();
    }

    @Override
    public void on_slash_register() {
        SlashCommandRegister reg = new SlashCommandRegister("pool", this.get_short_help(), SlashCommandRegister.CHAT_INPUT, new SlashCommandParameter[] {
                new SlashCommandParameter("description", "Pool description", SlashCommandParameter.STRING, true)
        });

        try
        {
            reg.doRegister(Discord.discord.token, Discord.discord.application_id);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String get_permission() {
        return null;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (ArrayUtils.contains(config_content.asStringArray(), event.getMessageId())) {
            MessageReaction reaction = event.getReaction();
            MessageReaction.ReactionEmote emote = reaction.getReactionEmote();

            if (emote.isEmoji()) {
                reaction.removeReaction(event.getUser()).complete();
            } else {
                if (!(emote.getEmote().getAsMention().equals(yes.getAsMention()) || emote.getEmote().getAsMention().equals(no.getAsMention()))) {
                    reaction.removeReaction().complete();
                }
            }
        }
    }
}
