package me.diax.bot.commands.statistics;

import me.diax.bot.DiaxBot;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Comportment on 14/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"stats", "statistics"})
public class StatisticsCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        List<JDA> shards = Arrays.asList(DiaxBot.SHARDS);
        trigger.getChannel().sendMessage(DiaxUtil.simpleEmbed(String.format("Shards: %s\nGuilds: %s\nUsers: %s", DiaxBot.SHARDS.length, shards.stream().flatMap(jda -> jda.getGuilds().stream().distinct()).count(), shards.stream().flatMap(jda -> jda.getUsers().stream().distinct()).count())).build()).queue();
    }
}