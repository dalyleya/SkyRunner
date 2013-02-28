package com.skyrunner.core;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 28.02.13
 * Time: 0:43
 * To change this template use File | Settings | File Templates.
 */
public class CyclicGameTimer {
    private long timeDelta;
    private long neededTime;

    private Runnable runnable;

    public CyclicGameTimer(long neededTime, Runnable runnable) {
        this.neededTime = neededTime;
        this.runnable = runnable;
    }

    public void process(long delta){
        timeDelta += delta;

        if (timeDelta > neededTime){
            timeDelta -= neededTime;
            runnable.run();
        }
    }

    public void reset(){
        timeDelta = 0;
    }
}
