package me.diax.bot.commands.miscellaneous;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Comportment on 12/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"echo", "repeat", "copy", "say"}, minimumArgs = 1)
public class EchoCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        try {
            trigger.delete().queue();
        } catch (Exception ignored) {
        }
        trigger.getChannel().sendMessage(args).queue();
    }
}
