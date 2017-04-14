package me.diax.bot.commands.statistics;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.command.DiaxCommands;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.Message;

import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Created by Comportment on 11/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"help", "?", "helpme"})
public class HelpCommand extends DiaxCommand {

    private final DiaxCommands commands;

    @Inject
    public HelpCommand(DiaxCommands commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Message trigger, String args) {
        trigger.getChannel().sendMessage(
                DiaxUtil.simpleEmbed(
                        "Help for Diax: " + DiaxUtil.paste(DiaxUtil.diaxGraffiti() + "\n\n\n\n\n\nCommands: \n\n" + commands.getCommands().stream().map(commands::newInstance).filter(command -> ! command.getOwnerOnly()).map(DiaxCommand::getHelpFormat).sorted().collect(Collectors.joining("\n"))
                        + "\n\nLinks:\n\n"+ String.join("\n", "Invite Me: https://discordapp.com/oauth2/authorise?client_id=295500621862404097&scope=bot&permissions=8", "My server: https://discord.gg/c6M8P", "My Patreon: https://www.patreon.com/Diax"))
                ).build()
        ).queue();
    }
}