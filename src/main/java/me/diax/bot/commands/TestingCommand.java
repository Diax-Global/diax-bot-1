package me.diax.bot.commands;

import me.diax.bot.lib.command.CommandProperty;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Comportment on 05/04/2017.
 * If you don't understand this, we are screwed.
 *
 * This class is an example command showing people who want to contribute what can go into a command.
 * Not all of the annotation are needed, but those are the current existing ones.
 */
@DiaxCommandDescription(triggers = "testing", properties = {
        @CommandProperty(key = "guild", value = "true"),
        @CommandProperty(key = "wat", value = "O.o"),
        @CommandProperty(key = "no", value = "you")
},minimumArgs = 2, ownerOnly = true, donorOnly = false, guildOnly = true, description = "Something about the command", permission = Permission.ADMINISTRATOR)
public class TestingCommand extends DiaxCommand {

    private String testing;

    @Inject
    public TestingCommand(@Named("diax.walshlist") String botlist) {
        this.testing = botlist;
    }
    /**
     * The message which triggered the command, the command args minus the prefix
     */
    @Override
    public void execute(Message trigger, String truncated) {
        trigger.getChannel().sendMessage(truncated + " " + testing).queue();
    }
}