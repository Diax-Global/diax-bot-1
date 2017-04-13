package me.diax.bot.commands.administrative;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 * Created by Comportment on 13/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"voicekick", "vkick"}, permission = Permission.KICK_MEMBERS, guildOnly = true, minimumArgs = 1)
public class VoiceKickCommand extends DiaxCommand {

    public void execute(Message trigger, String args) {
        trigger.getMentionedUsers().forEach(user -> {

            try {
                Member member  = trigger.getGuild().getMember(user);
                if (member.getVoiceState().inVoiceChannel()) {
                    trigger.getGuild().getController().createVoiceChannel("..").queue(voice -> trigger.getGuild().getController().moveVoiceMember(member, ((VoiceChannel) voice)).queue(_void -> voice.delete().queue()));
                } else {
                    trigger.getChannel().sendMessage(DiaxUtil.errorEmbed(DiaxUtil.makeName(user) + " is not in a voice channel.")).queue();
                }
            } catch (Exception e) {
                trigger.getChannel().sendMessage(DiaxUtil.errorEmbed("Could **not** voicekick " + DiaxUtil.makeName(user))).queue();
            }
        });
    }
}