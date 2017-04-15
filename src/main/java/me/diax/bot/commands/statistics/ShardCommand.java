package me.diax.bot.commands.statistics;

import me.diax.bot.DiaxBot;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Comportment on 11/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"shard"})
public class ShardCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        JDA.ShardInfo shardInfo = trigger.getJDA().getShardInfo();
        trigger.getChannel().sendMessage(DiaxUtil.simpleEmbed(String.format("♦ Shard: [%s/%s]",
                shardInfo != null ? shardInfo.getShardId() : 1,
                shardInfo != null ? shardInfo.getShardTotal() : DiaxBot.SHARDS.length
        )).build()).queue();
    }
}