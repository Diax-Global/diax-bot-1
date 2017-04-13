package me.diax.bot.lib.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import me.diax.bot.DiaxBot;
import net.dv8tion.jda.core.entities.TextChannel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Comportment on 10/04/2017.
 * If you don't understand this, we are screwed.
 */
public class DiaxDiscordAppender<E> extends AppenderBase<E> {

    private static long GUILD_ID = Long.valueOf(DiaxUtil.getGuildID());
    private static long CHANNEL_ID = 302048058907295747L;

    @Override
    protected void append(E eventObject) {
        if (eventObject instanceof ILoggingEvent) {
            ILoggingEvent event = (ILoggingEvent) eventObject;
            TextChannel channel = DiaxBot.SHARDS[DiaxUtil.getShard(GUILD_ID)].getGuildById(GUILD_ID).getTextChannelById(CHANNEL_ID);
            Date time = new Date(event.getTimeStamp());
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String loggerName = event.getLoggerName();
            if (loggerName.contains(".") && loggerName.lastIndexOf("." + 1) < loggerName.length())
                loggerName = loggerName.substring(loggerName.lastIndexOf(".") + 1);

            String eventString = "[" + formatter.format(time) + "] [" + event.getLevel() + "] [" + loggerName + "] " + event.getFormattedMessage();
            for (String string : DiaxUtil.splitStringEvery(eventString + "", 2000))
                channel.sendMessage(string).queue();
        }
    }
}