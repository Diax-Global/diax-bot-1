package me.diax.bot.tasks;

import com.mashape.unirest.http.Unirest;
import me.diax.bot.DiaxBot;
import me.diax.bot.lib.util.scheduler.DiaxTask;
import net.dv8tion.jda.core.JDA;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Comportment on 14/04/2017.
 * If you don't understand this, we are screwed.
 */
public class DiaxBotListUpdate extends DiaxTask {

    public void run() {
        try {
            JSONObject object;
            if (DiaxBot.SHARDS.length > 1) {
                object = new JSONObject().put("server_count", Arrays.stream(DiaxBot.SHARDS).mapToLong(jda -> jda.getGuilds().size()).sum());
            } else {
                List<Integer> amount = new ArrayList<>();
                for (JDA jda : DiaxBot.SHARDS) {
                    amount.add(jda.getGuilds().size());
                }
                object = new JSONObject().put("shards", amount);
            }
            DiaxBot.LOGGER.info("Update post returned: " + Unirest.post("https://walshydev.com/bot-list/api/bots/295500621862404097/stats")
                    .header("Authorization", "kUzuzrgDDTLUaR.QEBDwPgnCEpUEvZPFQlZnDdQdWzYXnWKRukkgqxJUNAwfwiLktIxWLl.EKWyXPfMgqWnGrWycuViRaT-tzvnh")
                    .header("Content-Type", "application/json")
                    .body(object).asJson().getStatusText());
        } catch (Exception ignored){}
    }
}
