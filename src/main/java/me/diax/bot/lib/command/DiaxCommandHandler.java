package me.diax.bot.lib.command;

import me.diax.bot.DiaxProperties;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by Comporment on 04/04/2017 at 22:32
 * Dev'ving like a sir since 1998. | https://github.com/Comportment
 */
public class DiaxCommandHandler extends ListenerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DiaxCommands commands;
    private final DiaxProperties properties;

    @Inject
    public DiaxCommandHandler(DiaxCommands commands, DiaxProperties properties) {
        this.commands = commands;
        this.properties = properties;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String content = event.getMessage().getRawContent();
        if (event.getAuthor().isBot() || ! content.startsWith(properties.getPrefix())) return;
        logger.info(String.format("[%s] %s", DiaxUtil.makeName(event.getAuthor()), content));
        String truncated = content.replaceFirst(properties.getPrefix(), "").trim();
        DiaxCommandDescription command = commands.find(truncated.split(" ")[0]);
        if (command != null) {
            execute(commands.newInstance(command), event.getMessage(), truncated);
        }
    }

    private void execute(DiaxCommand command, Message message, String args) {
        args = args.replaceFirst(args.split(" ")[0], "").trim();
        if (args.split(" ").length < command.getMinimumArgs()) {
            message.getChannel().sendMessage(DiaxUtil.errorEmbed("You did not specify enough arguments for this command.")).queue();
            return;
        }
        if (message.getChannelType().equals(ChannelType.TEXT)) {
            if (! DiaxUtil.checkPermission(message.getAuthor(), message.getGuild(), command.getPermission())) {
                message.getChannel().sendMessage(DiaxUtil.errorEmbed("You do not have enough permission to do that!")).queue();
                return;
            }
        } else {
            if (command.getGuildOnly()) {
                message.getChannel().sendMessage(DiaxUtil.errorEmbed("This command cannot be used in a private message.")).queue();
                return;
            }
        }
        if (command.getOwnerOnly() && ! message.getAuthor().getId().equals(DiaxUtil.getOwnerID())) {
            message.getChannel().sendMessage(DiaxUtil.errorEmbed("This is an owner only command, baka.")).queue();
            return;
        }
        try {
            command.execute(message, args);
        } catch (PermissionException e) {
            message.getChannel().sendMessage(DiaxUtil.errorEmbed("I do not have enough permission to do that.")).queue();
        } catch (Exception e) {
            message.getChannel().sendMessage("An error occurred, please contact Comportment#9489 with more info.").queue();
            e.printStackTrace();
        }
    }
}