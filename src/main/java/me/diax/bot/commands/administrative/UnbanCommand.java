package me.diax.bot.commands.administrative;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Comportment on 14/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"unban", "uban"}, permission = Permission.BAN_MEMBERS, guildOnly = true, minimumArgs = 1, ownerOnly = true)
public class UnbanCommand extends DiaxCommand {

    @Deprecated
    @Override
    public void execute(Message trigger, String truncated) {
        trigger.getMentionedUsers().forEach(user -> {
            try {
                trigger.getGuild().getController().unban(user).queue();
                trigger.getChannel().sendMessage(DiaxUtil.simpleEmbed(DiaxUtil.makeName(user) + " has been unbanned!").build()).queue();
            } catch (Exception exception) {
                trigger.getChannel().sendMessage(DiaxUtil.errorEmbed("Could not unban " + DiaxUtil.makeName(user))).queue();
            }
        });
    }
}
