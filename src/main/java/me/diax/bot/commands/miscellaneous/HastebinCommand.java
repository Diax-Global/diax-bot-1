package me.diax.bot.commands.miscellaneous;

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
@DiaxCommandDescription(triggers = {"hastebin", "haste"}, minimumArgs = 1)
public class HastebinCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String args) {
        String paste = paste(args);
        if (paste.startsWith("An error")) {
            trigger.getChannel().sendMessage(DiaxUtil.errorEmbed(paste)).queue();
            return;
        }
        trigger.getChannel().sendMessage(DiaxUtil.simpleEmbed(paste).build()).queue();
    }

    private static String paste(String content) {
        try {
            String pasteToken = Unirest.post("https://hastebin.com/documents")
                    .header("User-Agent", "Mantaro")
                    .header("Content-Type", "text/plain")
                    .body(content)
                    .asJson()
                    .getBody()
                    .getObject()
                    .getString("key");
            return "https://hastebin.com/" + pasteToken;
        } catch (UnirestException|JSONException exception) {
            return "An error occurred with uploading the content to hastebin.";
        }
    }
}