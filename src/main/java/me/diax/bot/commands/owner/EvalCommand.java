package me.diax.bot.commands.owner;

import me.diax.bot.lib.command.DiaxCommand;
import me.diax.bot.lib.command.DiaxCommandDescription;
import me.diax.bot.lib.util.DiaxUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;

/**
 * Created by Comportment on 07/04/2017.
 * If you don't understand this, we are screwed.
 */
@DiaxCommandDescription(triggers = {"eval", "hack", "evaluate"}, ownerOnly = true)
public class EvalCommand extends DiaxCommand {

    @Override
    public void execute(Message trigger, String truncated) {
        ScriptEngine engine = this.addMethods(new ScriptEngineManager().getEngineByName("nashorn"), trigger);
        String output;
        try {
            output = "" + engine.eval(String.join("\n", "load('nashorn:mozilla_compat.js');", "imports = new JavaImporter(java.util, java.io);", "(function(){", "with(imports){", truncated, "}", "})()"));
        } catch (Exception exception) {
            output = exception.getMessage();
        }
        trigger.getChannel().sendMessage(DiaxUtil.defaultEmbed().setDescription(String.format("```js\n%s ```", output)).build()).queue();
    }

    private ScriptEngine addMethods(ScriptEngine engine, Message trigger) {
        engine.put("jda", trigger.getJDA());
        engine.put("this", trigger);
        engine.put("event", trigger);
        engine.put("guild", trigger.getGuild());
        engine.put("channel", trigger.getChannel());
        engine.put("message", new MessageBuilder());
        engine.put("embed", new EmbedBuilder());
        return engine;
    }
}