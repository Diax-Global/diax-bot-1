package me.diax.bot.commands.random.number;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.Message;

import java.security.SecureRandom;

/**
 * Created by Comportment on 15/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"die", "dice", "roll"})
public class DieCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        trigger.getChannel().sendMessage(DiaxUtil.simpleEmbed("\uD83C\uDFB2 You rolled a " + (new SecureRandom().nextInt(6) + 1)).build()).queue();
    }
}
