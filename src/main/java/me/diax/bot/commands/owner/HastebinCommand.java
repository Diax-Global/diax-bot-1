package me.diax.bot.commands.owner;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.entities.Message;
import org.json.JSONException;

/**
 * Created by Comportment on 12/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"hastebin", "haste"}, minimumArgs = 1, ownerOnly = true)
public class HastebinCommand extends DiaxCommand {

    //TODO: Host pastes from Diax's site.
    @Override
    public void execute(Message trigger, String args) {
        String paste = DiaxUtil.paste(args);
        if (paste.startsWith("An error")) {
            trigger.getChannel().sendMessage(DiaxUtil.errorEmbed(paste)).queue();
            return;
        }
        trigger.getChannel().sendMessage(DiaxUtil.simpleEmbed(paste).build()).queue();
    }
}