package me.diax.bot;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.knockturnmc.api.util.ConfigurationUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.diax.bot.lib.audio.DiaxDisconnectListener;
import me.diax.bot.lib.command.DiaxCommandHandler;
import me.diax.bot.lib.util.DiaxLogger;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONException;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Comporment on 04/04/2017 at 19:45
 * Dev'ving like a sir since 1998. | https://github.com/Comportment
 */
public final class DiaxBot extends ListenerAdapter implements ComponentProvider, Module {

    public static final String VERSION;
    public static JDA[] SHARDS;
    public static int SHARD_AMOUNT;

    static {
        InputStreamReader reader = new InputStreamReader(DiaxBot.class.getResourceAsStream("/version"));
        BufferedReader txtReader = new BufferedReader(reader);
        String version;
        try {
            version = txtReader.readLine();
        } catch (IOException e) {
            version = "Unknown";
            LoggerFactory.getLogger(DiaxBot.class).error("Could not find the build of diax-bot.");
        }
        VERSION = version;
    }

    private final Injector injector;
    private final DiaxProperties properties;

    private DiaxBot() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        injector = Guice.createInjector(this);
        properties = ConfigurationUtils.loadConfiguration(classLoader, "diax.properties", new File(System.getProperty("user.dir")), DiaxProperties.class);
    }

    public static void main(String[] args) {
        new DiaxLogger();
        new DiaxBot().main();
    }

    private void main() {
        DiaxBot.SHARD_AMOUNT = this.getShardAmount();
        this.initialise();
    }

    public void onReady(ReadyEvent event) {
        if (event.getJDA().getShardInfo().getShardTotal() > DiaxBot.SHARD_AMOUNT) return;
        LoggerFactory.getLogger(this.getClass()).info("Users on startup: " + Arrays.stream(SHARDS).flatMap(shard -> shard.getUsers().stream().distinct()).count());
    }

    private void initialise() {
        DiaxBot.SHARDS = new JDA[DiaxBot.SHARD_AMOUNT];
        for (int i = 0; i < DiaxBot.SHARD_AMOUNT; i++) {
            JDA jda = null;
            try {
                JDABuilder builder = new JDABuilder(AccountType.BOT)
                        .addEventListener(injector.getInstance(DiaxCommandHandler.class), new DiaxDisconnectListener(), this)
                        .setAudioEnabled(true)
                        .setGame(Game.of(properties.getGame()))
                        .setToken(properties.getToken())
                        .setStatus(OnlineStatus.ONLINE);
                if (DiaxBot.SHARD_AMOUNT > 1) {
                    jda = builder.useSharding(i, DiaxBot.SHARD_AMOUNT).buildAsync();
                } else {
                    jda = builder.buildBlocking();
                }
            } catch (LoginException | RateLimitedException | InterruptedException ignored) {
            }
            if (jda != null) {
                DiaxBot.SHARDS[i] = jda;
            }
        }
    }

    private int getShardAmount() {
        try {
            HttpResponse<JsonNode> request = Unirest.get("https://discordapp.com/api/gateway/bot")
                    .header("Authorization", "Bot " + properties.getToken())
                    .header("Content-Type", "application/json").asJson();
            return Integer.parseInt(request.getBody().getObject().get("shards").toString()) + 1;
        } catch (UnirestException | JSONException | NumberFormatException | NullPointerException exception) {
            exception.printStackTrace();
        }
        return 2;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(ComponentProvider.class).toInstance(this);
        /* binder.bind(String.class)
               .annotatedWith(Names.named("diax.commands.prefix"))
                .toProvider(properties::getPrefix); */
        binder.bind(DiaxProperties.class).toProvider(() -> properties);
    }

    @Override
    public <E> E getInstance(Class<E> type) {
        return injector.getInstance(type);
    }
}