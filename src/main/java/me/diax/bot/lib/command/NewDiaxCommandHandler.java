package me.diax.bot.lib.command;

import me.diax.bot.lib.DiaxProperties;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by Comportment on 15/04/2017.
 * If you don't understand this, we are screwed. 
 */
public class NewDiaxCommandHandler extends ListenerAdapter {
    
    private final DiaxCommands commands;
    private final DiaxProperties properties;

    @Inject
    public NewDiaxCommandHandler(DiaxCommands commands, DiaxProperties properties) {
        this.commands = commands;
        this.properties = properties;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String content = event.getMessage().getRawContent().trim();
        Optional<String> prefix = properties.getPrefix().stream().filter(content::startsWith).findFirst();
        boolean valid = prefix.isPresent() || event.getChannelType().equals(ChannelType.PRIVATE);
        if (!valid || event.getAuthor().isBot() || event.getAuthor().isFake() ) return;
        content = prefix.isPresent() /* could be rewrote in a 'functional style' */ ? content.replaceFirst(prefix.get(), "").trim() : content.trim();
        DiaxCommandDescription command = commands.find(content.split(" ")[0]);
        if (command == null) return;
        TextChannel channel = event.getTextChannel();
        content = content.replaceFirst(content.split("")[0], "");
        if (content.split(" ").length < command.minimumArgs()) {
            channel.sendMessage(DiaxUtil.errorEmbed(""));
        }
        //Execute command
    }
}