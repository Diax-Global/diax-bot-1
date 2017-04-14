package me.diax.bot.lib.util.scheduler;

/**
 * Created by Comportment on 14/04/2017.
 * If you don't understand this, we are screwed.
 */
public abstract class DiaxTask implements Runnable {

    private String taskName;

    public DiaxTask(String taskName) {
        this.taskName = taskName;
    }

    public DiaxTask() {
        this.taskName = "DiaxTask-" + System.currentTimeMillis();
    }

    public void delay(long delay){
        DiaxScheduler.delayTask(this, delay);
    }

    public boolean repeat(long delay, long interval){
        return DiaxScheduler.scheduleRepeating(this, taskName, delay, interval);
    }

    public boolean cancel(){
        return DiaxScheduler.cancelTask(taskName);
    }

}
