package me.diax.bot.lib.util;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.diax.bot.DiaxBot;
import me.diax.bot.lib.audio.DiaxAudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.PermissionUtil;
import org.json.JSONException;

import java.awt.*;
import java.util.Random;

/**
 * Created by Comporment on 04/04/2017 at 22:55
 * Dev'ving like a sir since 1998. | https://github.com/Comportment
 */
public class DiaxUtil {

    public static String makeName(User user) {
        return String.format("%s#%s", user.getName(), user.getDiscriminator());
    }

    public static EmbedBuilder defaultEmbed() {
        return new EmbedBuilder().setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255))).setFooter(DiaxBot.VERSION, getAvatarUrl());
    }

    public static EmbedBuilder simpleEmbed(String message) {
        return defaultEmbed().setDescription(message);
    }

    public static MessageEmbed errorEmbed(String message) {
        return defaultEmbed().setColor(new Color(111, 16, 10)).addField("⛔ Error ⛔", message, false).build();
    }

    public static MessageEmbed musicEmbed(String message) {
        return defaultEmbed().addField("\uD83C\uDFB5 Music \uD83C\uDFB5", message, false).build();
    }

    public static String getTrackInfo(DiaxAudioTrack track) {
        return String.format("`%s ` by `%s `\nRequested by `%s `", track.getTrack().getInfo().title, track.getTrack().getInfo().author, makeName(track.getRequester()));
    }

    public static String getOwnerID() {
        return "293884638101897216";
    }

    public static long getGuildID() {
        return 293889712014360586L;
    }

    public static boolean checkPermission(User user, Guild guild, Permission permission) {
        return user.getId().equals(getOwnerID()) || PermissionUtil.checkPermission(guild, guild.getMember(user), permission);
    }

    public static int getShard(long guildID) {
        return (int) (guildID >> 22) % DiaxBot.SHARDS.length;
    }

    public static int getShard(Guild guild) {
        long id = Long.valueOf(guild.getId());
        return getShard(id);
    }

    public static String[] splitStringEvery(String s, int interval) {
        int size = (int) Math.ceil(((s.length() / (double) interval)));
        String[] result = new String[size];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        }
        result[lastIndex] = s.substring(j);
        return result;
    }

    public static String paste(String content) {
        try {
            String pasteToken = Unirest.post("https://hastebin.com/documents")
                    .header("User-Agent", "Diax")
                    .header("Content-Type", "text/plain")
                    .body(content)
                    .asJson()
                    .getBody()
                    .getObject()
                    .getString("key");
            return "https://hastebin.com/" + pasteToken;
        } catch (UnirestException |JSONException exception) {
            return "An error occurred with uploading the content to hastebin.";
        }
    }

    public static String diaxGraffiti() {
        return "oooooooooo.    o8o                           \n" +
                "`888'   `Y8b   `\"'                         \n" +
                " 888      888 oooo   .oooo.   oooo    ooo   \n" +
                " 888      888 `888  `P  )88b   `88b..8P'    \n" +
                " 888      888  888   .oP\"888     Y888'     \n" +
                " 888     d88'  888  d8(  888   .o8\"'88b    \n" +
                "o888bood8P'   o888o `Y888\"\"8o o88'   888o   ";
    }

    public static String getAvatarUrl() {
        return "https://cdn.discordapp.com/icons/293889712014360586/32a876d0e467fe7227f50e3bcfc45bd2.jpg";
    }

    public static String getAnimal(String type) {
        try {
            return Unirest.get("http://shibe.online/api/" + type + "/?count=1").header("User-Agent", "Diax").header("Content-Type", "application/json").asJson().getBody().getArray().getString(0);
        } catch (UnirestException exception) {
            return "An error occurred.";
        }
    }
}