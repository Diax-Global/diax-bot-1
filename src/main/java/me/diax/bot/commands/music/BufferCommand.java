package me.diax.bot.commands.music;

import me.diax.bot.lib.audio.DiaxGuildMusicManager;
import me.diax.bot.lib.audio.DiaxTrackScheduler;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.TimeUtils;
import net.dv8tion.jda.core.entities.Message;
import org.slf4j.Logger;

/**
 * Created by NachtRaben on 4/5/2017.
 */

@DiaxCommandDescription(triggers = {"buffer", "position"}, guildOnly = true, description = "Buffers to the desired portion of the current track.")
public class BufferCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String truncated) {
        DiaxGuildMusicManager manager = DiaxGuildMusicManager.getManagerFor(trigger.getGuild());
        DiaxTrackScheduler scheduler = manager.scheduler;
        if (scheduler.getCurrentTrack() != null && scheduler.getCurrentTrack().getTrack().isSeekable()) {
            long time = TimeUtils.stringToMillis(truncated);

            if(scheduler.getCurrentTrack().getTrack().getInfo().length >= time) {
                scheduler.getCurrentTrack().getTrack().setPosition(time);
                trigger.getTextChannel().sendMessage("Skipping to, `" + TimeUtils.millisToString(time, TimeUtils.FormatType.TIME) + "`.").queue();
            } else {
                trigger.getTextChannel().sendMessage("You can only skip up too, `" + TimeUtils.millisToString(scheduler.getCurrentTrack().getTrack().getInfo().length, TimeUtils.FormatType.TIME) + "`.").queue();
            }
        }
    }
    
}