package me.diax.bot.commands.music;

import me.diax.bot.lib.audio.DiaxAudioTrack;
import me.diax.bot.lib.audio.DiaxGuildMusicManager;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.Message;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Comportment on 14/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"queue"}, guildOnly = true)
public class QueueCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        StringBuilder list = new StringBuilder();

        final BlockingQueue<DiaxAudioTrack> queue = DiaxGuildMusicManager.getManagerFor(trigger.getGuild()).scheduler.getQueue();
        if (queue.isEmpty()) {
            trigger.getChannel().sendMessage(DiaxUtil.musicEmbed("The queue is empty.")).queue();
            return;
        }
        int i = 0;
        list.append("Now playing: " + DiaxGuildMusicManager.getManagerFor(trigger.getGuild()).scheduler.getCurrentTrack().getQueueFormat()).append("\n");
        for (DiaxAudioTrack track : queue) {
            i++;
            list.append(i).append(")").append(track.getQueueFormat()).append("\n");
        }
        trigger.getChannel().sendMessage(DiaxUtil.musicEmbed("Here's the queue: " + DiaxUtil.paste(list.toString()))).queue();
    }
}
