package me.diax.bot.commands.administrative;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

/**
 * Created by Comportment on 13/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"purge", "clean", "clear", "prune", "cls"}, minimumArgs = 1, guildOnly = true, permission = Permission.MESSAGE_MANAGE)
public class PurgeCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        int value;
        final TextChannel channel = trigger.getTextChannel();
        try {
            value = Integer.parseInt(args.split(" ")[0]);
        } catch (NumberFormatException exception) {
            channel.sendMessage(DiaxUtil.errorEmbed("That is not a valid number.")).queue();
            return;
        }
        if (value < 2) value = 2;
        channel.getHistory().retrievePast(Math.min(value, 100)).queue(messageHistory -> {
            messageHistory = messageHistory.stream().filter(message -> ! message.getCreationTime()
                    .isBefore(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList());

            final int size = messageHistory.size();
            trigger.getTextChannel().deleteMessages(messageHistory).queue(_void -> {
                channel.sendMessage(DiaxUtil.simpleEmbed(size + " messages have been deleted.").build()).queue();
            });
        });
    }
}