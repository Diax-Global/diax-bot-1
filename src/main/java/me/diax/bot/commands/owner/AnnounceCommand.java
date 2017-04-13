package me.diax.bot.commands.owner;

import me.diax.bot.DiaxBot;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Comportment on 13/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription
public class AnnounceCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        DiaxBot.SHARDS[DiaxUtil.getShard(DiaxUtil.getGuildID())]
                .getGuildById(DiaxUtil.getGuildID())
                .getTextChannelById(302006615757029377L)
                .sendMessage(
                        DiaxUtil.defaultEmbed()
                                .setTitle("Diax Official Announcement", trigger.getAuthor().getAvatarUrl())
                                .setDescription(args).build()).queue();
    }
}
